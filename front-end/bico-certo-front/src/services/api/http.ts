const API_BASE_URL = "http://localhost:8080";

type UnauthorizedHandler = (() => void) | null;

let unauthorizedHandler: UnauthorizedHandler = null;
let isHandlingUnauthorized = false;

export function setUnauthorizedHandler(fn: UnauthorizedHandler) {
  unauthorizedHandler = typeof fn === "function" ? fn : null;
}

type HttpBody = BodyInit | Record<string, unknown> | null | undefined;

type HttpOptions = Omit<RequestInit, "body" | "headers"> & {
  token?: string;
  signal?: AbortSignal;
  headers?: Record<string, string>;
  body?: HttpBody;
};

export async function http<T = unknown>(
  path: string,
  { token, signal, headers = {}, body, ...options }: HttpOptions = {}
): Promise<T> {
  const isFormData =
    typeof FormData !== "undefined" && body instanceof FormData;

  const mergedHeaders: Record<string, string> = {
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...headers,
  };

  if (!isFormData && !mergedHeaders["Content-Type"]) {
    mergedHeaders["Content-Type"] = "application/json";
  }

  const finalBody =
    body == null
      ? undefined
      : isFormData
      ? body
      : typeof body === "string"
      ? body
      : JSON.stringify(body);

  let response: Response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      signal,
      body: finalBody,
      headers: mergedHeaders,
    });
  } catch (err: unknown) {
    if (err instanceof Error && err.name === "AbortError") throw err;

    throw new Error("Não foi possível conectar ao servidor.");
  }

  const text = await response.text();
  const json = text ? safeJson(text) : null;
  const data = json ?? (text || null);

  if (response.status === 401) {
    const shouldAutoLogout = path !== "/auth/login" && !!unauthorizedHandler;

    if (shouldAutoLogout && !isHandlingUnauthorized) {
      isHandlingUnauthorized = true;

      try {
        unauthorizedHandler?.();
      } finally {
        setTimeout(() => {
          isHandlingUnauthorized = false;
        }, 0);
      }
    }

    const message =
      (isObject(json) && (json.message || json.error)) ||
      "Sessão expirada.";

    throw new Error(String(message));
  }

  if (!response.ok) {
    const message =
      (isObject(json) && (json.message || json.error)) ||
      (typeof data === "string" && data) ||
      `HTTP ${response.status}`;

    throw new Error(String(message));
  }

  return data as T;
}

function safeJson(text: string): unknown {
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
}

function isObject(value: unknown): value is Record<string, unknown> {
  return typeof value === "object" && value !== null;
}