package org.eclipse.paho.android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Iterator;
import java.util.UUID;
import org.eclipse.paho.android.service.MessageStore;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class DatabaseMessageStore implements MessageStore {
    private static final String ARRIVED_MESSAGE_TABLE_NAME = "MqttArrivedMessageTable";
    private static final String MTIMESTAMP = "mtimestamp";
    private static final String TAG = "DatabaseMessageStore";
    private SQLiteDatabase db = null;
    private MQTTDatabaseHelper mqttDb;
    private MqttTraceHandler traceHandler;

    /* loaded from: classes3.dex */
    private static class MQTTDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mqttAndroidService.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TAG = "MQTTDatabaseHelper";
        private MqttTraceHandler traceHandler;

        public MQTTDatabaseHelper(MqttTraceHandler traceHandler, Context context) {
            super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
            this.traceHandler = null;
            this.traceHandler = traceHandler;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase database) {
            this.traceHandler.traceDebug(TAG, "onCreate {CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);}");
            try {
                database.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
                this.traceHandler.traceDebug(TAG, "created the table");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onCreate", e);
                throw e;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            this.traceHandler.traceDebug(TAG, "onUpgrade");
            try {
                db.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
                onCreate(db);
                this.traceHandler.traceDebug(TAG, "onUpgrade complete");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onUpgrade", e);
                throw e;
            }
        }
    }

    public DatabaseMessageStore(MqttService service, Context context) {
        this.mqttDb = null;
        this.traceHandler = null;
        this.traceHandler = service;
        this.mqttDb = new MQTTDatabaseHelper(this.traceHandler, context);
        this.traceHandler.traceDebug(TAG, "DatabaseMessageStore<init> complete");
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public String storeArrived(String clientHandle, String topic, MqttMessage message) {
        this.db = this.mqttDb.getWritableDatabase();
        this.traceHandler.traceDebug(TAG, "storeArrived{" + clientHandle + "}, {" + message.toString() + "}");
        byte[] payload = message.getPayload();
        int qos = message.getQos();
        boolean isRetained = message.isRetained();
        boolean isDuplicate = message.isDuplicate();
        ContentValues contentValues = new ContentValues();
        String uuid = UUID.randomUUID().toString();
        contentValues.put(MqttServiceConstants.MESSAGE_ID, uuid);
        contentValues.put(MqttServiceConstants.CLIENT_HANDLE, clientHandle);
        contentValues.put(MqttServiceConstants.DESTINATION_NAME, topic);
        contentValues.put(MqttServiceConstants.PAYLOAD, payload);
        contentValues.put(MqttServiceConstants.QOS, Integer.valueOf(qos));
        contentValues.put(MqttServiceConstants.RETAINED, Boolean.valueOf(isRetained));
        contentValues.put(MqttServiceConstants.DUPLICATE, Boolean.valueOf(isDuplicate));
        contentValues.put(MTIMESTAMP, Long.valueOf(System.currentTimeMillis()));
        try {
            this.db.insertOrThrow(ARRIVED_MESSAGE_TABLE_NAME, null, contentValues);
            this.traceHandler.traceDebug(TAG, "storeArrived: inserted message with id of {" + uuid + "} - Number of messages in database for this clientHandle = " + getArrivedRowCount(clientHandle));
            return uuid;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "onUpgrade", e);
            throw e;
        }
    }

    private int getArrivedRowCount(String clientHandle) {
        Cursor query = this.db.query(ARRIVED_MESSAGE_TABLE_NAME, new String[]{MqttServiceConstants.MESSAGE_ID}, "clientHandle=?", new String[]{clientHandle}, null, null, null);
        int i = query.moveToFirst() ? query.getInt(0) : 0;
        query.close();
        return i;
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public boolean discardArrived(String clientHandle, String id) {
        this.db = this.mqttDb.getWritableDatabase();
        this.traceHandler.traceDebug(TAG, "discardArrived{" + clientHandle + "}, {" + id + "}");
        try {
            int delete = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, "messageId=? AND clientHandle=?", new String[]{id, clientHandle});
            if (delete != 1) {
                this.traceHandler.traceError(TAG, "discardArrived - Error deleting message {" + id + "} from database: Rows affected = " + delete);
                return false;
            }
            this.traceHandler.traceDebug(TAG, "discardArrived - Message deleted successfully. - messages in db for this clientHandle " + getArrivedRowCount(clientHandle));
            return true;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "discardArrived", e);
            throw e;
        }
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public Iterator<MessageStore.StoredMessage> getAllArrivedMessages(final String clientHandle) {
        return new Iterator<MessageStore.StoredMessage>(clientHandle) { // from class: org.eclipse.paho.android.service.DatabaseMessageStore.1
            private Cursor c;
            private boolean hasNext;
            private final String[] selectionArgs;
            final /* synthetic */ String val$clientHandle;

            {
                this.val$clientHandle = clientHandle;
                String[] strArr = {clientHandle};
                this.selectionArgs = strArr;
                DatabaseMessageStore.this.db = DatabaseMessageStore.this.mqttDb.getWritableDatabase();
                if (clientHandle == null) {
                    this.c = DatabaseMessageStore.this.db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, null, null, null, null, null, "mtimestamp ASC");
                } else {
                    this.c = DatabaseMessageStore.this.db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, null, "clientHandle=?", strArr, null, null, "mtimestamp ASC");
                }
                this.hasNext = this.c.moveToFirst();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (!this.hasNext) {
                    this.c.close();
                }
                return this.hasNext;
            }

            @Override // java.util.Iterator
            public MessageStore.StoredMessage next() {
                Cursor cursor = this.c;
                String string = cursor.getString(cursor.getColumnIndex(MqttServiceConstants.MESSAGE_ID));
                Cursor cursor2 = this.c;
                String string2 = cursor2.getString(cursor2.getColumnIndex(MqttServiceConstants.CLIENT_HANDLE));
                Cursor cursor3 = this.c;
                String string3 = cursor3.getString(cursor3.getColumnIndex(MqttServiceConstants.DESTINATION_NAME));
                Cursor cursor4 = this.c;
                byte[] blob = cursor4.getBlob(cursor4.getColumnIndex(MqttServiceConstants.PAYLOAD));
                Cursor cursor5 = this.c;
                int i = cursor5.getInt(cursor5.getColumnIndex(MqttServiceConstants.QOS));
                Cursor cursor6 = this.c;
                boolean parseBoolean = Boolean.parseBoolean(cursor6.getString(cursor6.getColumnIndex(MqttServiceConstants.RETAINED)));
                Cursor cursor7 = this.c;
                boolean parseBoolean2 = Boolean.parseBoolean(cursor7.getString(cursor7.getColumnIndex(MqttServiceConstants.DUPLICATE)));
                MqttMessageHack mqttMessageHack = new MqttMessageHack(blob);
                mqttMessageHack.setQos(i);
                mqttMessageHack.setRetained(parseBoolean);
                mqttMessageHack.setDuplicate(parseBoolean2);
                this.hasNext = this.c.moveToNext();
                return new DbStoredData(string, string2, string3, mqttMessageHack);
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            protected void finalize() throws Throwable {
                this.c.close();
                super.finalize();
            }
        };
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public void clearArrivedMessages(String clientHandle) {
        int delete;
        this.db = this.mqttDb.getWritableDatabase();
        String[] strArr = {clientHandle};
        if (clientHandle == null) {
            this.traceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table");
            delete = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, null, null);
        } else {
            this.traceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table of " + clientHandle + " messages");
            delete = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, "clientHandle=?", strArr);
        }
        this.traceHandler.traceDebug(TAG, "clearArrivedMessages: rows affected = " + delete);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class DbStoredData implements MessageStore.StoredMessage {
        private String clientHandle;
        private MqttMessage message;
        private String messageId;
        private String topic;

        DbStoredData(String messageId, String clientHandle, String topic, MqttMessage message) {
            this.messageId = messageId;
            this.topic = topic;
            this.message = message;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public String getMessageId() {
            return this.messageId;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public String getClientHandle() {
            return this.clientHandle;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public String getTopic() {
            return this.topic;
        }

        @Override // org.eclipse.paho.android.service.MessageStore.StoredMessage
        public MqttMessage getMessage() {
            return this.message;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class MqttMessageHack extends MqttMessage {
        public MqttMessageHack(byte[] payload) {
            super(payload);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.paho.client.mqttv3.MqttMessage
        public void setDuplicate(boolean dup) {
            super.setDuplicate(dup);
        }
    }

    @Override // org.eclipse.paho.android.service.MessageStore
    public void close() {
        SQLiteDatabase sQLiteDatabase = this.db;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }
}
