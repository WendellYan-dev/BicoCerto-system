// auth.context.ts
import { createContext } from "react";

export type AuthContextType = {
  token: string | null;
  setToken: (token: string | null) => void;
};

// Não passe um objeto vazio se não for compatível com o tipo
export const AuthContext = createContext<AuthContextType | undefined>(undefined);