package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Role;
import model.User;
import model.UserSummary;
import service.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class ReportController implements HttpHandler {

    private final UserService userService;

    public ReportController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            sendResponse(exchange, 401, "{\"error\":\"Unauthorized\"}");
            return;
        }

        String decoded;
        try {
            decoded = new String(Base64.getDecoder().decode(authHeader.substring(6)), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            sendResponse(exchange, 401, "{\"error\":\"Unauthorized\"}");
            return;
        }
        String[] parts = decoded.split(":", 2);
        if (parts.length != 2) {
            sendResponse(exchange, 401, "{\"error\":\"Unauthorized\"}");
            return;
        }

        Optional<User> userOpt = userService.authenticate(parts[0], parts[1]);
        if (userOpt.isEmpty()) {
            sendResponse(exchange, 401, "{\"error\":\"Unauthorized\"}");
            return;
        }

        User user = userOpt.get();
        if (user.getRole() == Role.USER) {
            sendResponse(exchange, 403, "{\"error\":\"Forbidden: USER role is not allowed\"}");
            return;
        }

        UserSummary summary = userService.getUsersSummary();
        String json = String.format(
                "{\"totalUsers\":%d,\"adminCount\":%d,\"managerCount\":%d,\"userCount\":%d}",
                summary.getTotalUsers(), summary.getAdminCount(),
                summary.getManagerCount(), summary.getUserCount()
        );
        sendResponse(exchange, 200, json);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
