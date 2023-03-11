import axios from "axios";
import { SERVER_URL } from "../../constants";

const HttpCommon = axios.create({
    baseURL: SERVER_URL,
    headers: {
        "Content-Type": "application/json"
    }
});

export default HttpCommon;