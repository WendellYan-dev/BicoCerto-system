// AuthProvider.tsx
import { useState } from "react";
import type { ReactNode } from "react";
import { AuthContext } from "./auth.context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("token"));

  function handleSetToken(newToken: string | null) {
    setToken(newToken);
    if (newToken) localStorage.setItem("token", newToken);
    else localStorage.removeItem("token");
  }

  // No React 19, use apenas <AuthContext> em vez de <AuthContext.Provider>
  return (
    <AuthContext value={{ token, setToken: handleSetToken }}>
      {children}
    </AuthContext>
  );
}