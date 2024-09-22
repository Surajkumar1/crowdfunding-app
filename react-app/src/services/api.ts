import axios from "axios";
import { SignUpResponse } from "../context/AuthContext";
import { ProjectResponse, ProjectListResponse } from "../context/ProjectContext";

const API_BASE_URL = "http://localhost:8080/"; // Replace with actual API URL

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json', // Default Content-Type
    'Authorization': 'Bearer YOUR_TOKEN' // Optional: Set your auth token here if needed
  }
});

interface userData {
  userName: string
}

// Example functions for API calls
export const signupUser = async (userName: string, emailId: string, password: string, role: string) => {
  return await api.post<SignUpResponse>("/api/v1/auth/signup", {userName, emailId, password, role});
};

export const loginUser = async (username: string, password: string) => {
  return await api.post("/api/v1/auth/signin", { username, password });
};

export const createProject = async (project: {
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  goalAmount: string;
}) => {
  console.log("yo yo")
  return await api.post("/api/v1/campaign/create", project);
};

export const getProject = async (id: string) => {
  return await api.get("/api/v1/campaign/" + id);
};

export const getDonation = async (id: string) => {
  return await api.get("/api/v1/campaign/" + id);
};

export const getProjectList = async (data: {user_id: string, page_num: string, page_size: string}) => {
  let url = "/api/v1/campaign/browse";
  let param = 0
  if(data.user_id != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "user_id=" + data.user_id;
  }
  if(data.page_num != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_num=" + data.page_num;
  }
  if(data.page_size != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_size=" + data.page_size;
  }
  return await api.get(url);
};

export const getUserProject = async (data: {page_num: string, page_size: string})  => {
  let url = "/api/v1/campaign/my/projects";
  let param = 0
  if(data.page_num != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_num=" + data.page_num;
  }
  if(data.page_size != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_size=" + data.page_size;
  }
  return await api.get(url);
};

export const getUserDonations = async (data: {page_num: string, page_size: string})  => {
  let url = "/api/v1/donation/my/donations";
  let param = 0
  if(data.page_num != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_num=" + data.page_num;
  }
  if(data.page_size != null){
    if(param == 0) {
      url = url + "?";
    } else {
      url = url + "&";
    }
    param += 1;
    url = url + "page_size=" + data.page_size;
  }
  return await api.get(url);
};

export const donateToProject = async (projectId: number, amount: number) => {
  return await api.post(`/api/v1/donation/${projectId}/donate`, { amount });
};

export const updatePayment = async (payment: {paymentId: number, token: string, status: string} ) => {
  return await api.post(`/api/v1/payment/update`, payment);
};
