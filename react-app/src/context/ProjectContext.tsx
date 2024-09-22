import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import {
  createProject,
  getProjectList,
  donateToProject,
  getProject,
  getDonation,
  getUserProject,
  getUserDonations,
} from "../services/api";
import { useAuthContext } from "./AuthContext";

export interface DonationResponse {
  statusCode: string;
  message: string;
  data: Donation;
}

export interface DonationListResponse {
  statusCode: string;
  message: string;
  data: Donation[];
}

export interface ProjectListResponse {
  statusCode: string;
  message: string;
  data: Project[];
}

export interface CreateDonationResponse {
  statusCode: string;
  message: string;
  data: {
    paymentId: number;
  };
}

export interface ProjectResponse {
  statusCode: string;
  message: string;
  data: Project;
}


export interface CreateProjectResponse {
  statusCode: string;
  message: string;
}

export interface Donation {
  id: number;
  campaign: Project;
  userId: string;
  amount: number;
}

export interface Project {
  id: number;
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  goalAmount: string;
  status: string;
  donations: number;
  isEffective: boolean;
  message: string
}

interface ProjectContextType {
  projects: Project[];
  addProject: (project: Project) => Promise<CreateProjectResponse>;
  donate: (id: number, amount: number) => Promise<CreateDonationResponse>;
  fetchProject: (id: string) => Promise<ProjectResponse>;
  fetchProjectList: (data: {
    user_id: string;
    page_num: string;
    page_size: string;
  }) => Promise<ProjectListResponse>;
  fetchUserProjects: (data: {
    page_num: string;
    page_size: string;
  }) => Promise<ProjectListResponse>;
  fetchUserDonations: (data: {
    page_num: string;
    page_size: string;
  }) => Promise<DonationListResponse>;
  fetchDonation: (id: string) => Promise<DonationResponse>;
}

const ProjectContext = createContext<ProjectContextType | undefined>(undefined);

export const ProjectProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [projects, setProjects] = useState<Project[]>([]);
  const { user } = useAuthContext();

  const addProject = async (project: Project): Promise<CreateProjectResponse> => {
    const response = await createProject(project);
    return response.data;
  };

  const donate = async (
    id: number,
    amount: number
  ): Promise<CreateDonationResponse> => {
    const response = await donateToProject(id, amount);
    return response.data;
  };

  const fetchProject = async (id: string): Promise<ProjectResponse> => {
    const response = await getProject(id);
    if (response.status != 200) {
      return response.data;
    }
    return response.data;
  };

  const fetchUserProjects = async (data: {
    page_num: string;
    page_size: string;
  }): Promise<ProjectListResponse> => {
    const response = await getUserProject(data);
    if (response.status != 200) {
      return response.data;
    }
    return response.data;
  };

  const fetchProjectList = async (data: {
    user_id: string;
    page_num: string;
    page_size: string;
  }): Promise<ProjectListResponse> => {
    const response = await getProjectList(data);
    return response.data;
  };

  const fetchDonation = async (id: string): Promise<DonationResponse> => {
    const response = await getDonation(id);
    return response.data;
  };

  const fetchUserDonations = async (data: {
    page_num: string;
    page_size: string;
  }): Promise<DonationListResponse> => {
    const response = await getUserDonations(data);
    return response.data;
  };

  return (
    <ProjectContext.Provider
      value={{
        projects,
        addProject,
        donate,
        fetchProject,
        fetchDonation,
        fetchProjectList,
        fetchUserProjects,
        fetchUserDonations,
      }}
    >
      {children}
    </ProjectContext.Provider>
  );
};

export const useProjectContext = () => {
  const context = useContext(ProjectContext);
  if (!context) {
    throw new Error("useProjectContext must be used within a ProjectProvider");
  }
  return context;
};
