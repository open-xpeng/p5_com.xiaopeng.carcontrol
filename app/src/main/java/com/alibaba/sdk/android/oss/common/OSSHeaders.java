package com.alibaba.sdk.android.oss.common;

import com.alibaba.sdk.android.oss.common.utils.HttpHeaders;

/* loaded from: classes.dex */
public interface OSSHeaders extends HttpHeaders {
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    public static final String ACCESS_CONTROL_REQUEST_HEADER = "Access-Control-Request-Headers";
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String COPY_OBJECT_METADATA_DIRECTIVE = "x-oss-metadata-directive";
    public static final String COPY_OBJECT_SOURCE = "x-oss-copy-source";
    public static final String COPY_OBJECT_SOURCE_IF_MATCH = "x-oss-copy-source-if-match";
    public static final String COPY_OBJECT_SOURCE_IF_MODIFIED_SINCE = "x-oss-copy-source-if-modified-since";
    public static final String COPY_OBJECT_SOURCE_IF_NONE_MATCH = "x-oss-copy-source-if-none-match";
    public static final String COPY_OBJECT_SOURCE_IF_UNMODIFIED_SINCE = "x-oss-copy-source-if-unmodified-since";
    public static final String COPY_SOURCE_RANGE = "x-oss-copy-source-range";
    public static final String GET_OBJECT_IF_MATCH = "If-Match";
    public static final String GET_OBJECT_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String GET_OBJECT_IF_NONE_MATCH = "If-None-Match";
    public static final String GET_OBJECT_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    public static final String HEAD_OBJECT_IF_MATCH = "If-Match";
    public static final String HEAD_OBJECT_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String HEAD_OBJECT_IF_NONE_MATCH = "If-None-Match";
    public static final String HEAD_OBJECT_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    public static final String ORIGIN = "origin";
    public static final String OSS_CANNED_ACL = "x-oss-acl";
    public static final String OSS_HASH_CRC64_ECMA = "x-oss-hash-crc64ecma";
    public static final String OSS_HASH_SHA1 = "x-oss-hash-sha1";
    public static final String OSS_HEADER_REQUEST_ID = "x-oss-request-id";
    public static final String OSS_HEADER_SYMLINK_TARGET = "x-oss-symlink-target";
    public static final String OSS_NEXT_APPEND_POSITION = "x-oss-next-append-position";
    public static final String OSS_OBJECT_TYPE = "x-oss-object-type";
    public static final String OSS_PREFIX = "x-oss-";
    public static final String OSS_SECURITY_TOKEN = "x-oss-security-token";
    public static final String OSS_SERVER_SIDE_ENCRYPTION = "x-oss-server-side-encryption";
    public static final String OSS_USER_METADATA_PREFIX = "x-oss-meta-";
    public static final String OSS_VERSION_ID = "x-oss-version-id";
    public static final String STORAGE_CLASS = "x-oss-storage-class";
}
