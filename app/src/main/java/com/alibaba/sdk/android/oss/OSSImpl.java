package com.alibaba.sdk.android.oss;

import android.content.Context;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLogToFileUtils;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.ExtensionRequestOperation;
import com.alibaba.sdk.android.oss.internal.InternalRequestOperation;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.internal.ObjectURLPresigner;
import com.alibaba.sdk.android.oss.model.AbortMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.AbortMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.AppendObjectRequest;
import com.alibaba.sdk.android.oss.model.AppendObjectResult;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.CopyObjectRequest;
import com.alibaba.sdk.android.oss.model.CopyObjectResult;
import com.alibaba.sdk.android.oss.model.CreateBucketRequest;
import com.alibaba.sdk.android.oss.model.CreateBucketResult;
import com.alibaba.sdk.android.oss.model.DeleteBucketLifecycleRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketLifecycleResult;
import com.alibaba.sdk.android.oss.model.DeleteBucketLoggingRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketLoggingResult;
import com.alibaba.sdk.android.oss.model.DeleteBucketRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketResult;
import com.alibaba.sdk.android.oss.model.DeleteMultipleObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteMultipleObjectResult;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteObjectResult;
import com.alibaba.sdk.android.oss.model.GeneratePresignedUrlRequest;
import com.alibaba.sdk.android.oss.model.GetBucketACLRequest;
import com.alibaba.sdk.android.oss.model.GetBucketACLResult;
import com.alibaba.sdk.android.oss.model.GetBucketInfoRequest;
import com.alibaba.sdk.android.oss.model.GetBucketInfoResult;
import com.alibaba.sdk.android.oss.model.GetBucketLifecycleRequest;
import com.alibaba.sdk.android.oss.model.GetBucketLifecycleResult;
import com.alibaba.sdk.android.oss.model.GetBucketLoggingRequest;
import com.alibaba.sdk.android.oss.model.GetBucketLoggingResult;
import com.alibaba.sdk.android.oss.model.GetBucketRefererRequest;
import com.alibaba.sdk.android.oss.model.GetBucketRefererResult;
import com.alibaba.sdk.android.oss.model.GetObjectACLRequest;
import com.alibaba.sdk.android.oss.model.GetObjectACLResult;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.GetSymlinkRequest;
import com.alibaba.sdk.android.oss.model.GetSymlinkResult;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.alibaba.sdk.android.oss.model.ImagePersistRequest;
import com.alibaba.sdk.android.oss.model.ImagePersistResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.ListBucketsRequest;
import com.alibaba.sdk.android.oss.model.ListBucketsResult;
import com.alibaba.sdk.android.oss.model.ListMultipartUploadsRequest;
import com.alibaba.sdk.android.oss.model.ListMultipartUploadsResult;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.ListPartsRequest;
import com.alibaba.sdk.android.oss.model.ListPartsResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.PutBucketLifecycleRequest;
import com.alibaba.sdk.android.oss.model.PutBucketLifecycleResult;
import com.alibaba.sdk.android.oss.model.PutBucketLoggingRequest;
import com.alibaba.sdk.android.oss.model.PutBucketLoggingResult;
import com.alibaba.sdk.android.oss.model.PutBucketRefererRequest;
import com.alibaba.sdk.android.oss.model.PutBucketRefererResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.PutSymlinkRequest;
import com.alibaba.sdk.android.oss.model.PutSymlinkResult;
import com.alibaba.sdk.android.oss.model.RestoreObjectRequest;
import com.alibaba.sdk.android.oss.model.RestoreObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.alibaba.sdk.android.oss.model.TriggerCallbackRequest;
import com.alibaba.sdk.android.oss.model.TriggerCallbackResult;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class OSSImpl implements OSS {
    private ClientConfiguration conf;
    private OSSCredentialProvider credentialProvider;
    private URI endpointURI;
    private ExtensionRequestOperation extensionRequestOperation;
    private InternalRequestOperation internalRequestOperation;

    public OSSImpl(Context context, String str, OSSCredentialProvider oSSCredentialProvider, ClientConfiguration clientConfiguration) {
        OSSLogToFileUtils.init(context.getApplicationContext(), clientConfiguration);
        try {
            String trim = str.trim();
            this.endpointURI = new URI(trim.startsWith("http") ? trim : "http://" + trim);
            if (oSSCredentialProvider == null) {
                throw new IllegalArgumentException("CredentialProvider can't be null.");
            }
            Boolean bool = false;
            try {
                bool = Boolean.valueOf(OSSUtils.isValidateIP(this.endpointURI.getHost()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.endpointURI.getScheme().equals("https") && bool.booleanValue()) {
                throw new IllegalArgumentException("endpoint should not be format with https://ip.");
            }
            this.credentialProvider = oSSCredentialProvider;
            this.conf = clientConfiguration == null ? ClientConfiguration.getDefaultConf() : clientConfiguration;
            this.internalRequestOperation = new InternalRequestOperation(context.getApplicationContext(), this.endpointURI, oSSCredentialProvider, this.conf);
            this.extensionRequestOperation = new ExtensionRequestOperation(this.internalRequestOperation);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("Endpoint must be a string like 'http://oss-cn-****.aliyuncs.com',or your cname like 'http://image.cnamedomain.com'!");
        }
    }

    public OSSImpl(Context context, OSSCredentialProvider oSSCredentialProvider, ClientConfiguration clientConfiguration) {
        this.credentialProvider = oSSCredentialProvider;
        this.conf = clientConfiguration == null ? ClientConfiguration.getDefaultConf() : clientConfiguration;
        this.internalRequestOperation = new InternalRequestOperation(context.getApplicationContext(), oSSCredentialProvider, this.conf);
        this.extensionRequestOperation = new ExtensionRequestOperation(this.internalRequestOperation);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ListBucketsResult> asyncListBuckets(ListBucketsRequest listBucketsRequest, OSSCompletedCallback<ListBucketsRequest, ListBucketsResult> oSSCompletedCallback) {
        return this.internalRequestOperation.listBuckets(listBucketsRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ListBucketsResult listBuckets(ListBucketsRequest listBucketsRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.listBuckets(listBucketsRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<CreateBucketResult> asyncCreateBucket(CreateBucketRequest createBucketRequest, OSSCompletedCallback<CreateBucketRequest, CreateBucketResult> oSSCompletedCallback) {
        return this.internalRequestOperation.createBucket(createBucketRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public CreateBucketResult createBucket(CreateBucketRequest createBucketRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.createBucket(createBucketRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<DeleteBucketResult> asyncDeleteBucket(DeleteBucketRequest deleteBucketRequest, OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult> oSSCompletedCallback) {
        return this.internalRequestOperation.deleteBucket(deleteBucketRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public DeleteBucketResult deleteBucket(DeleteBucketRequest deleteBucketRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.deleteBucket(deleteBucketRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetBucketInfoResult> asyncGetBucketInfo(GetBucketInfoRequest getBucketInfoRequest, OSSCompletedCallback<GetBucketInfoRequest, GetBucketInfoResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getBucketInfo(getBucketInfoRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetBucketInfoResult getBucketInfo(GetBucketInfoRequest getBucketInfoRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getBucketInfo(getBucketInfoRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetBucketACLResult> asyncGetBucketACL(GetBucketACLRequest getBucketACLRequest, OSSCompletedCallback<GetBucketACLRequest, GetBucketACLResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getBucketACL(getBucketACLRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetBucketACLResult getBucketACL(GetBucketACLRequest getBucketACLRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getBucketACL(getBucketACLRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<PutBucketRefererResult> asyncPutBucketReferer(PutBucketRefererRequest putBucketRefererRequest, OSSCompletedCallback<PutBucketRefererRequest, PutBucketRefererResult> oSSCompletedCallback) {
        return this.internalRequestOperation.putBucketReferer(putBucketRefererRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public PutBucketRefererResult putBucketReferer(PutBucketRefererRequest putBucketRefererRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.putBucketReferer(putBucketRefererRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetBucketRefererResult getBucketReferer(GetBucketRefererRequest getBucketRefererRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getBucketReferer(getBucketRefererRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetBucketRefererResult> asyncGetBucketReferer(GetBucketRefererRequest getBucketRefererRequest, OSSCompletedCallback<GetBucketRefererRequest, GetBucketRefererResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getBucketReferer(getBucketRefererRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public DeleteBucketLoggingResult deleteBucketLogging(DeleteBucketLoggingRequest deleteBucketLoggingRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.deleteBucketLogging(deleteBucketLoggingRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<DeleteBucketLoggingResult> asyncDeleteBucketLogging(DeleteBucketLoggingRequest deleteBucketLoggingRequest, OSSCompletedCallback<DeleteBucketLoggingRequest, DeleteBucketLoggingResult> oSSCompletedCallback) {
        return this.internalRequestOperation.deleteBucketLogging(deleteBucketLoggingRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public PutBucketLoggingResult putBucketLogging(PutBucketLoggingRequest putBucketLoggingRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.putBucketLogging(putBucketLoggingRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<PutBucketLoggingResult> asyncPutBucketLogging(PutBucketLoggingRequest putBucketLoggingRequest, OSSCompletedCallback<PutBucketLoggingRequest, PutBucketLoggingResult> oSSCompletedCallback) {
        return this.internalRequestOperation.putBucketLogging(putBucketLoggingRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetBucketLoggingResult getBucketLogging(GetBucketLoggingRequest getBucketLoggingRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getBucketLogging(getBucketLoggingRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetBucketLoggingResult> asyncGetBucketLogging(GetBucketLoggingRequest getBucketLoggingRequest, OSSCompletedCallback<GetBucketLoggingRequest, GetBucketLoggingResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getBucketLogging(getBucketLoggingRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public PutBucketLifecycleResult putBucketLifecycle(PutBucketLifecycleRequest putBucketLifecycleRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.putBucketLifecycle(putBucketLifecycleRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<PutBucketLifecycleResult> asyncPutBucketLifecycle(PutBucketLifecycleRequest putBucketLifecycleRequest, OSSCompletedCallback<PutBucketLifecycleRequest, PutBucketLifecycleResult> oSSCompletedCallback) {
        return this.internalRequestOperation.putBucketLifecycle(putBucketLifecycleRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetBucketLifecycleResult getBucketLifecycle(GetBucketLifecycleRequest getBucketLifecycleRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getBucketLifecycle(getBucketLifecycleRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetBucketLifecycleResult> asyncGetBucketLifecycle(GetBucketLifecycleRequest getBucketLifecycleRequest, OSSCompletedCallback<GetBucketLifecycleRequest, GetBucketLifecycleResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getBucketLifecycle(getBucketLifecycleRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public DeleteBucketLifecycleResult deleteBucketLifecycle(DeleteBucketLifecycleRequest deleteBucketLifecycleRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.deleteBucketLifecycle(deleteBucketLifecycleRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<DeleteBucketLifecycleResult> asyncDeleteBucketLifecycle(DeleteBucketLifecycleRequest deleteBucketLifecycleRequest, OSSCompletedCallback<DeleteBucketLifecycleRequest, DeleteBucketLifecycleResult> oSSCompletedCallback) {
        return this.internalRequestOperation.deleteBucketLifecycle(deleteBucketLifecycleRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<PutObjectResult> asyncPutObject(PutObjectRequest putObjectRequest, OSSCompletedCallback<PutObjectRequest, PutObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.putObject(putObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncPutObject(putObjectRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetObjectResult> asyncGetObject(GetObjectRequest getObjectRequest, OSSCompletedCallback<GetObjectRequest, GetObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getObject(getObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetObjectResult getObject(GetObjectRequest getObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getObject(getObjectRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetObjectACLResult> asyncGetObjectACL(GetObjectACLRequest getObjectACLRequest, OSSCompletedCallback<GetObjectACLRequest, GetObjectACLResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getObjectACL(getObjectACLRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetObjectACLResult getObjectACL(GetObjectACLRequest getObjectACLRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.getObjectACL(getObjectACLRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<DeleteObjectResult> asyncDeleteObject(DeleteObjectRequest deleteObjectRequest, OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.deleteObject(deleteObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public DeleteObjectResult deleteObject(DeleteObjectRequest deleteObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.deleteObject(deleteObjectRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<DeleteMultipleObjectResult> asyncDeleteMultipleObject(DeleteMultipleObjectRequest deleteMultipleObjectRequest, OSSCompletedCallback<DeleteMultipleObjectRequest, DeleteMultipleObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.deleteMultipleObject(deleteMultipleObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public DeleteMultipleObjectResult deleteMultipleObject(DeleteMultipleObjectRequest deleteMultipleObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.deleteMultipleObject(deleteMultipleObjectRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<AppendObjectResult> asyncAppendObject(AppendObjectRequest appendObjectRequest, OSSCompletedCallback<AppendObjectRequest, AppendObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.appendObject(appendObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public AppendObjectResult appendObject(AppendObjectRequest appendObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncAppendObject(appendObjectRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<HeadObjectResult> asyncHeadObject(HeadObjectRequest headObjectRequest, OSSCompletedCallback<HeadObjectRequest, HeadObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.headObject(headObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public HeadObjectResult headObject(HeadObjectRequest headObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.headObject(headObjectRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<CopyObjectResult> asyncCopyObject(CopyObjectRequest copyObjectRequest, OSSCompletedCallback<CopyObjectRequest, CopyObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.copyObject(copyObjectRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.copyObject(copyObjectRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ListObjectsResult> asyncListObjects(ListObjectsRequest listObjectsRequest, OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> oSSCompletedCallback) {
        return this.internalRequestOperation.listObjects(listObjectsRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ListObjectsResult listObjects(ListObjectsRequest listObjectsRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.listObjects(listObjectsRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<InitiateMultipartUploadResult> asyncInitMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest, OSSCompletedCallback<InitiateMultipartUploadRequest, InitiateMultipartUploadResult> oSSCompletedCallback) {
        return this.internalRequestOperation.initMultipartUpload(initiateMultipartUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public InitiateMultipartUploadResult initMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.initMultipartUpload(initiateMultipartUploadRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<UploadPartResult> asyncUploadPart(UploadPartRequest uploadPartRequest, OSSCompletedCallback<UploadPartRequest, UploadPartResult> oSSCompletedCallback) {
        return this.internalRequestOperation.uploadPart(uploadPartRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncUploadPart(uploadPartRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncCompleteMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest, OSSCompletedCallback<CompleteMultipartUploadRequest, CompleteMultipartUploadResult> oSSCompletedCallback) {
        return this.internalRequestOperation.completeMultipartUpload(completeMultipartUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncCompleteMultipartUpload(completeMultipartUploadRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<AbortMultipartUploadResult> asyncAbortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest, OSSCompletedCallback<AbortMultipartUploadRequest, AbortMultipartUploadResult> oSSCompletedCallback) {
        return this.internalRequestOperation.abortMultipartUpload(abortMultipartUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public AbortMultipartUploadResult abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.abortMultipartUpload(abortMultipartUploadRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ListPartsResult> asyncListParts(ListPartsRequest listPartsRequest, OSSCompletedCallback<ListPartsRequest, ListPartsResult> oSSCompletedCallback) {
        return this.internalRequestOperation.listParts(listPartsRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ListPartsResult listParts(ListPartsRequest listPartsRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.listParts(listPartsRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ListMultipartUploadsResult> asyncListMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest, OSSCompletedCallback<ListMultipartUploadsRequest, ListMultipartUploadsResult> oSSCompletedCallback) {
        return this.internalRequestOperation.listMultipartUploads(listMultipartUploadsRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ListMultipartUploadsResult listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.listMultipartUploads(listMultipartUploadsRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public void updateCredentialProvider(OSSCredentialProvider oSSCredentialProvider) {
        this.credentialProvider = oSSCredentialProvider;
        this.internalRequestOperation.setCredentialProvider(oSSCredentialProvider);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<CompleteMultipartUploadResult> asyncMultipartUpload(MultipartUploadRequest multipartUploadRequest, OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult> oSSCompletedCallback) {
        return this.extensionRequestOperation.multipartUpload(multipartUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public CompleteMultipartUploadResult multipartUpload(MultipartUploadRequest multipartUploadRequest) throws ClientException, ServiceException {
        return this.extensionRequestOperation.multipartUpload(multipartUploadRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ResumableUploadResult> asyncResumableUpload(ResumableUploadRequest resumableUploadRequest, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> oSSCompletedCallback) {
        return this.extensionRequestOperation.resumableUpload(resumableUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ResumableUploadResult resumableUpload(ResumableUploadRequest resumableUploadRequest) throws ClientException, ServiceException {
        return this.extensionRequestOperation.resumableUpload(resumableUploadRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ResumableUploadResult> asyncSequenceUpload(ResumableUploadRequest resumableUploadRequest, OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> oSSCompletedCallback) {
        return this.extensionRequestOperation.sequenceUpload(resumableUploadRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ResumableUploadResult sequenceUpload(ResumableUploadRequest resumableUploadRequest) throws ClientException, ServiceException {
        return this.extensionRequestOperation.sequenceUpload(resumableUploadRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public String presignConstrainedObjectURL(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws ClientException {
        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf).presignConstrainedURL(generatePresignedUrlRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public String presignConstrainedObjectURL(String str, String str2, long j) throws ClientException {
        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf).presignConstrainedURL(str, str2, j);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public String presignPublicObjectURL(String str, String str2) {
        return new ObjectURLPresigner(this.endpointURI, this.credentialProvider, this.conf).presignPublicURL(str, str2);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public boolean doesObjectExist(String str, String str2) throws ClientException, ServiceException {
        return this.extensionRequestOperation.doesObjectExist(str, str2);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public void abortResumableUpload(ResumableUploadRequest resumableUploadRequest) throws IOException {
        this.extensionRequestOperation.abortResumableUpload(resumableUploadRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<TriggerCallbackResult> asyncTriggerCallback(TriggerCallbackRequest triggerCallbackRequest, OSSCompletedCallback<TriggerCallbackRequest, TriggerCallbackResult> oSSCompletedCallback) {
        return this.internalRequestOperation.triggerCallback(triggerCallbackRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public TriggerCallbackResult triggerCallback(TriggerCallbackRequest triggerCallbackRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.asyncTriggerCallback(triggerCallbackRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<ImagePersistResult> asyncImagePersist(ImagePersistRequest imagePersistRequest, OSSCompletedCallback<ImagePersistRequest, ImagePersistResult> oSSCompletedCallback) {
        return this.internalRequestOperation.imageActionPersist(imagePersistRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public ImagePersistResult imagePersist(ImagePersistRequest imagePersistRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.imageActionPersist(imagePersistRequest, null).getResult();
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public PutSymlinkResult putSymlink(PutSymlinkRequest putSymlinkRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncPutSymlink(putSymlinkRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<PutSymlinkResult> asyncPutSymlink(PutSymlinkRequest putSymlinkRequest, OSSCompletedCallback<PutSymlinkRequest, PutSymlinkResult> oSSCompletedCallback) {
        return this.internalRequestOperation.putSymlink(putSymlinkRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public GetSymlinkResult getSymlink(GetSymlinkRequest getSymlinkRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncGetSymlink(getSymlinkRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<GetSymlinkResult> asyncGetSymlink(GetSymlinkRequest getSymlinkRequest, OSSCompletedCallback<GetSymlinkRequest, GetSymlinkResult> oSSCompletedCallback) {
        return this.internalRequestOperation.getSymlink(getSymlinkRequest, oSSCompletedCallback);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public RestoreObjectResult restoreObject(RestoreObjectRequest restoreObjectRequest) throws ClientException, ServiceException {
        return this.internalRequestOperation.syncRestoreObject(restoreObjectRequest);
    }

    @Override // com.alibaba.sdk.android.oss.OSS
    public OSSAsyncTask<RestoreObjectResult> asyncRestoreObject(RestoreObjectRequest restoreObjectRequest, OSSCompletedCallback<RestoreObjectRequest, RestoreObjectResult> oSSCompletedCallback) {
        return this.internalRequestOperation.restoreObject(restoreObjectRequest, oSSCompletedCallback);
    }
}
