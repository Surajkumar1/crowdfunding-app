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

const ProjectList: React.FC = () => {
  const { fetchProjectList } = useProjectContext();
  const [error, setError] = useState<string | null>(null);
  const [projects, setProjects] = useState<Project[] | null>(null);
  const [hasFetched, setHasFetched] = useState<boolean>(false);
  const queryParams = new URLSearchParams(location.search);
  let user_id = queryParams.get("user_id");
  user_id = user_id === null ? "" : user_id;
  const page_num = queryParams.get("page_num");
  const page_size = queryParams.get("page_size");

  console.log("yo");
  const fetchData = async () => {
    // Function to perform the login

    if (hasFetched) return;
    setHasFetched(true);
    const getProject = async () => {
      try {
        const request = { data: { user_id, page_num, page_size } };
        const response = await fetchProjectList({
          user_id,
          page_num,
          page_size,
        });
        console.log(response);
        if (response.statusCode == "200") {
          setProjects(response.data);
        } else {
          console.log("yoo " + response.message);
          setError(response.message);
        }
      } catch (error) {
        console.error("Login failed:", error);
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
      <h1>Our Projects</h1>
      <div className="project-container">
        {projects.map((project) => (
          <div key={project.id} className="project-card">
            <h2 className="project-title">{project.title}</h2>
            <p className="project-description">{project.description}</p>
            <p className="project-donations">Donations: {project.donations}</p>
            <p className="project-goal">Goal Amount: ${project.goalAmount}</p>
            <DonationForm projectId={project.id} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProjectList;
