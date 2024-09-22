import React, { useState, useEffect, Component } from "react";
import {
  Project as ProjectType,
  ProjectListResponse,
} from "../context/ProjectContext";
import DonationForm from "../components/DonationForm";
import {
  useProjectContext,
  DonationResponse,
  Project,
} from "../context/ProjectContext";
import { useParams } from "react-router-dom";
import "../ProjectDetails.css";
import RedirectionButton from '../button/RedirectionButton'; 

const UserProjectList: React.FC = () => {
  const { fetchUserProjects } = useProjectContext();
  const [error, setError] = useState<string | null>(null);
  const [projects, setProjects] = useState<Project[] | null>(null);
  const [hasFetched, setHasFetched] = useState<boolean>(false);
  const queryParams = new URLSearchParams(location.search);
  let page_num = queryParams.get("page_num");
  page_num = page_num === null ? "" : page_num;
  let page_size = queryParams.get("page_size");
  page_size = page_size === null ? "" : page_size;

  const fetchData = async () => {
    if (hasFetched) return;
    setHasFetched(true);
    const getProject = async () => {
      try {
        const response = await fetchUserProjects({
          page_num,
          page_size,
        });
        console.log(response);
        if (response.statusCode == "200") {
          setProjects(response.data);
        } else {
          setError(response.message);
        }
      } catch (error) {
      }
    };
    getProject();
  };

  fetchData();

  if (projects == null) {
    return (
      <div className="project-list">
        <h1>No projects available</h1>
      </div>
    );
  }

  return (
    <div className="project-list">
      <h1>My Campaigns</h1>
      <div className="project-container">
        {projects.map((project) => (
          <div key={project.id} className="project-card">
            <h2 className="project-title">{project.title}</h2>
            <p className="project-description">{project.description}</p>
            <p className="project-donations">Donations: {project.donations}</p>
            <p className="project-goal">Goal Amount: ${project.goalAmount}</p>
            <p className="project-messsage">{project.message}</p>
          </div>
        ))}
      </div>
      <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default UserProjectList;
