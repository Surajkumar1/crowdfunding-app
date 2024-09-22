import React from "react";
import ProjectForm from "../components/ProjectForm";
import RedirectionButton from '../button/RedirectionButton'; 

const CreateProject: React.FC = () => {
  return (
    <div>
    <div className="create-project">
      <ProjectForm />
    </div>
    <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default CreateProject;
