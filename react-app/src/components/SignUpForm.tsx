import React, { useState } from "react";
import { useAuthContext, SignUpResponse } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const SignUpForm: React.FC = () => {
  const { signup } = useAuthContext();
  const [username, setUsername] = useState("");
  const [emailId, setEmailId] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // Basic validation
    if (!username || !emailId || !password) {
      setError("All fields are required");
      return;
    }

    setError(null);
    // Handle form submission
    const userData = {
      username,
      emailId,
      password,
      role,
    };
    console.log("role " + role);
    const response = await signup(username, emailId, password, role);
    if (response.statusCode == "200") {
      navigate("/");
    } else {
      setError(response.message);
    }
  };

  return (
    <div>
      <h2>Signup Form</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={emailId}
            onChange={(e) => setEmailId(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="role">Select your role:</label>
          <select
            id="role"
            value={role}
            onChange={(e) => setRole(e.target.value)}
          >
            <option value="" disabled>
              Select a role
            </option>
            <option value="DONOR">DONOR</option>
            <option value="CREATOR">CREATOR</option>
          </select>
        </div>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
};

export default SignUpForm;
