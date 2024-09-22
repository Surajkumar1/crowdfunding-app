import React, { useState } from "react";
import { useProjectContext } from "../context/ProjectContext";
import { useNavigate } from "react-router-dom";

const ProjectForm: React.FC = () => {
  const { addProject } = useProjectContext();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [goalAmount, setGoalAmount] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Hi");
    await addProject({
      title,
      description,
      startDate,
      endDate,
      goalAmount,
      id: 0,
      donations: 0,
      status: "ACTIVE",
    });
    navigate("/");
  };

  return (
    <div className="form-container">
      <h1>Create a New Project</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Project Title"
          required
        />
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Project Description"
          required
        />
        <input
          type="text"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
          placeholder="Project Start Date"
          required
        />
        <input
          type="text"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
          placeholder="Project End Date"
          required
        />
        <input
          type="text"
          value={goalAmount}
          onChange={(e) => setGoalAmount(e.target.value)}
          placeholder="Project Goal Amount"
          required
        />
        <button type="submit">Create Project</button>
      </form>
    </div>
  );
};

export default ProjectForm;