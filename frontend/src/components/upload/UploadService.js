import HttpCommon from "./HttpCommon";

const upload = (file, onUploadProgress, token) => {
    let formData = new FormData();

    formData.append("file", file);
    return HttpCommon.post("/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
            "Authorization": token
        },
        onUploadProgress,
    });
};

const getFiles = (token) => {
    return HttpCommon.get("/files",
    {
        headers: {
            "Authorization": token
        },
    });
};

const getFile = (token, uuid) => {
    return HttpCommon.get("/file/" + uuid,
    {
        headers: {
            "Authorization": token
        },
    });
};

const deleteFile = (token, uuid) => {
    return HttpCommon.delete("/file/" + uuid,
    {
        headers: {
            "Authorization": token
        },
    });
};

const UploadService = {upload, getFiles, getFile, deleteFile};

export default UploadService;