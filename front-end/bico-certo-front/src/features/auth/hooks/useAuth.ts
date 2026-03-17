// useAuth.ts
import { useContext } from "react";
import { AuthContext } from "../../../app/providers/auth.context";
import { loginRequestApi } from "../services/authService";

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth deve ser usado dentro de um AuthProvider");
  }

  const { token, setToken } = context;

  async function signIn(login: string, password: string) {
    const response = await loginRequestApi({ login, password });
    setToken(response.token);
  }

  function logout() {
    setToken(null);
  }

  return { token, signIn, logout, isAuthenticated: !!token };
}