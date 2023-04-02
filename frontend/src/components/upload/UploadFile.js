import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import UploadService from "./UploadService";

const UploadFile = (props) => {
    const [selectedFiles, setSelectedFiles] = useState(undefined);
    const [currentFile, setCurrentFile] = useState(undefined);
    const [progress, setProgress] = useState(0);
    const [message, setMessage] = useState("");
    const [count, setCount] = useState(0);
    

    const token = sessionStorage.getItem("jwt");

    // useEffect(() => {
    //     UploadService.getFiles().then(response => setFileInfo(response.data))
    // }, []);

    const selectFile = (event) => setSelectedFiles(event.target.files);

    const remove = (index) => {
        console.log(index);
        let file = props.fileInfo[index];
        //find the position of uuid. It follow 'download/' string. Search for 'd' character
        //then add the length of 'download/' string to get the position of uuid
        let pos = file.url.indexOf('download/') + 9;
        //Get uuid from link
        let uuid = file.url.substring(pos, file.url.length);
        UploadService.deleteFile(token, uuid);
        //Remove file at current index of the uploaded list file
        props.fileInfo.splice(index,1)
        //let listFile = props.fileInfo;
        //props.setFileInfo(listFile);
        setCount(count-1);
    };

    const upload = () => {
        let currentFile = selectedFiles[0];

        setProgress(0);
        setCurrentFile(currentFile);

        UploadService.upload(currentFile, (event) => {
            setProgress(Math.round((100 * event.loaded) / event.total));
        }, token)
        .then((response) => {
            setMessage(response.data.message);
            //Get image uuid
            let uuid = response.data.Id;

            //Call getFile to get the link of image
            return UploadService.getFile(token, uuid);
        })
        .then(response => props.setFileInfo( oldFileInfo => [...oldFileInfo, response.data]))
        .catch(() => {
            setProgress(0);
            setMessage("Could not upload the file");
            setCurrentFile(undefined);
        });
        setSelectedFiles(undefined);
        setCount(count+1);
    };

    return (
        <div>
            {currentFile && (
            <div className="progress">
                <div
                    className="progress-bar progress-bar-info progress-bar-striped"
                    role="progressbar"
                    aria-valuenow={progress}
                    aria-valuemin="0"
                    aria-valuemax="100"
                    style={{ width: progress + "%" }}
                >
                {progress}%
                </div>
            </div>
            )}

            <label className="btn btn-default">
                <input type="file" onChange={selectFile} />
            </label>

            <button
                className="btn btn-success"
                disabled={!selectedFiles}
                onClick={upload}
            >
                Upload
            </button>

            <div className="alert alert-light" role="alert">
                {message}
            </div>

            <div className="card">
            <div className="card-header">List of images</div>
                <ul className="list-group list-group-flush">
                {props.fileInfo &&
                    props.fileInfo.map((file, index) => (
                    <li className="list-group-item" key={index}>
                        <a href={file.url}>{file.name}</a>
                        <button
                            align="center"
                            className="btn btn-success"
                            // todo: replace by delete icon disabled={selectedRemove}
                            onClick={() => remove(index)}
                        >
                            Remove
                        </button>
                        
                    </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default UploadFile;