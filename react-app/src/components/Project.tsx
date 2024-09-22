import React, { useState, useEffect, Component } from "react";
import { Project as ProjectType } from "../context/ProjectContext";
import DonationForm from "../components/DonationForm";
import { useProjectContext, ProjectResponse } from "../context/ProjectContext";
import { useParams } from "react-router-dom";
import "../ProjectDetails.css";

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
      </div>
    );
  }

  let data = project.data;
  return (
    <div className="project-details">
      <h2>{data.title}</h2>
      <p>
        <strong>Description:</strong>
        {data.description}
      </p>
      <p>
        <strong>startDate:</strong>
        {data.startDate}
      </p>
      <p>
        <strong>startDate:</strong>
        {data.endDate}
      </p>
      <p>
        <strong>startDate:</strong>
        {data.goalAmount}
      </p>
      <p>
        <strong>startDate:</strong>
        {data.status}
      </p>
      <p>
        <strong>Donations:startDate:</strong> {data.goalAmount}
      </p>
      <DonationForm projectId={data.id} />
    </div>
  );
};

export default Project;
