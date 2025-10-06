import static spark.Spark.*;

import com.google.gson.Gson;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = Database.getConnection();
        TutoriaDAO dao = new TutoriaDAO(conn);

        Gson gson = new Gson();

        port(8080);

        post("/tutorias", (req, res) -> {
            Tutoria t = gson.fromJson(req.body(), Tutoria.class);
            t.setEstado("PENDIENTE");
            Tutoria saved = dao.save(t);
            res.type("application/json");
            return gson.toJson(saved);
        });

        get("/tutorias/user/:userId", (req, res) -> {
            Long userId = Long.parseLong(req.params("userId"));
            List<Tutoria> tutorias = dao.findByUserId(userId);
            res.type("application/json");
            return gson.toJson(tutorias);
        });

        delete("/tutorias/:id", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));
            dao.delete(id);
            res.status(204);
            return "";
        });

        put("/tutorias/:id/estado", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));
            Tutoria t = gson.fromJson(req.body(), Tutoria.class);
            Tutoria updated = dao.updateEstado(id, t.getEstado());
            res.type("application/json");
            return gson.toJson(updated);
        });
    }
}
