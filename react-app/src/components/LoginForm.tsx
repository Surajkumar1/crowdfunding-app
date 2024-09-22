import React, { useState } from "react";
import { useAuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const LoginForm: React.FC = () => {
  const { login } = useAuthContext();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Basic validation
    if (!username || !password) {
      setError("All fields are required");
      return;
    }

    setError(null);
    const response = await login(username, password);
    if (response.statusCode == "200") {
      navigate("/");
    } else {
      setError(response.message);
    }
  };

  return (
    <div className="form-container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
          required
        />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          required
        />
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginForm;
