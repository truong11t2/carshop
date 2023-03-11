import HttpCommon from "./HttpCommon";

const upload = (file, onUploadProgress) => {
    let formData = new FormData();

    formData.append("file", file);
    return HttpCommon.post("/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
    });
};

const getFiles = () => {
    return HttpCommon.get("/files");
};

const UploadService = {upload, getFiles,};

export default UploadService;