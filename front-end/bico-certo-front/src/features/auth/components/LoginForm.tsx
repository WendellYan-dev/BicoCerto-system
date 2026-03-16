import { useState } from "react";

type Props = {
  onSubmit: (login: string, password: string) => Promise<void>;
};

export default function LoginForm({ onSubmit }: Props) {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await onSubmit(login, password);
  }

  return (
    <form onSubmit={handleSubmit}>
      <input
        placeholder="Login"
        value={login}
        onChange={(e) => setLogin(e.target.value)}
      />

      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button type="submit">Entrar</button>
    </form>
  );
}