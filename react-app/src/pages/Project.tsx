import React, { useState, useEffect, Component } from "react";
import { Project as ProjectType } from "../context/ProjectContext";
import DonationForm from "../components/DonationForm";
import { useProjectContext, ProjectResponse } from "../context/ProjectContext";
import { useParams } from "react-router-dom";
import "../ProjectDetails.css";
import RedirectionButton from '../button/RedirectionButton'; 

const Project: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { fetchProject } = useProjectContext();
  const [error, setError] = useState<string | null>(null);
  const [project, setProject] = useState<ProjectResponse | null>(null);
  const [hasFetched, setHasFetched] = useState<boolean>(false);

  console.log("yo");
  const fetchData = async () => {
    // Function to perform the login

    if (hasFetched) return;
    setHasFetched(true);
    const getProject = async () => {
      try {
        if (id != null) {
          const response = await fetchProject(id);
          if (response.statusCode == "200") {
            setProject(response);
          } else {
            setError(response.message);
          }
        }
      } catch (error) {
        console.error("Login failed:", error);
      }
    };
    getProject();
  };

  fetchData();

  if (project == null) {
    return (
      <div>
        <h2>Invalid project id</h2>
        <RedirectionButton name="Home page" url="/" />
      </div>
    );
  }

  let data = project.data;
  return (
    <div>
    <div className="project-details">
      <h2>{data.title}</h2>
      <p>
        <strong>Description:</strong>
        {data.description}
      </p>
      <p>
        <strong>Start Date:</strong>
        {data.startDate}
      </p>
      <p>
        <strong>End Date:</strong>
        {data.endDate}
      </p>
      <p>
        <strong>Goal Amount:</strong>
        {data.goalAmount}
      </p>
      <p>
        <strong>status:</strong>
        {data.message}
      </p>
      {data.isEffective ? <p>
        <strong>Donations: </strong> {data.donations}
      </p> : null}
      <DonationForm projectId={data.id} />
      
    </div>
    <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default Project;
