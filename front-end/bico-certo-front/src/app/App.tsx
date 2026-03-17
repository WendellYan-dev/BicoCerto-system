import { Routes} from "react-router-dom";
import { Route } from "react-router-dom";
import LoginPage from "../features/auth/pages/LoginPage";

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
}