import { http } from "../../../services/api/http";

export type LoginRequest = {
  login: string;
  password: string;
};

export type LoginResponse = {
  token: string;
};

export function loginRequestApi(data: LoginRequest) {
  return http<LoginResponse>("/auth/login", {
    method: "POST",
    body: data,
  });
}