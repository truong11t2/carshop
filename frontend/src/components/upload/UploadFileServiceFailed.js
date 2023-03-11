/* import React from "react";
import { SERVER_URL } from "../../constants";

function Upload() {
    const input = document.getElementById('fileInput');
    const token = sessionStorage.getItem("jwt");

    const upload = (file) => {
        fetch(SERVER_URL + 'upload', {
            method: 'POST',
            headers: {
                'Content-Type':'multipart/form-data',
                'Authorization' : token
            },
            body: file
        })
        .then(response => {
            if(response.ok) {
                //call function display small images
            } else {
                alert('Something went wrong!');
            }
        })
        .catch(err => console.error(err));
    }
} */

import HttpCommon from "./HttpCommon";

class UploadFileService {
    upload(file, onUploadProgress) {
        let formData = new FormData();

        formData.append("file", file);

        return HttpCommon.post("upload", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
            onUploadProgress,
        });
    }

    getFiles() {
        return HttpCommon.get("files")
    }
}

export default new UploadFileService();
