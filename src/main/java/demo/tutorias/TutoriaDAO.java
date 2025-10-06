import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TutoriaDAO {
    private Connection conn;

    public TutoriaDAO(Connection conn) {
        this.conn = conn;
    }

    public Tutoria save(Tutoria t) throws SQLException {
        String sql = "INSERT INTO tutorias (tutor_id, estudiante_id, materia_id, fecha, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setLong(1, t.getTutorId());
        stmt.setLong(2, t.getEstudianteId());
        stmt.setLong(3, t.getMateriaId());
        stmt.setTimestamp(4, Timestamp.valueOf(t.getFecha()));
        stmt.setString(5, t.getEstado());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            t.setId(rs.getLong(1));
        }
        rs.close();
        stmt.close();
        return t;
    }

    public List<Tutoria> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM tutorias WHERE estudiante_id = ? OR tutor_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, userId);
        stmt.setLong(2, userId);
        ResultSet rs = stmt.executeQuery();

        List<Tutoria> tutorias = new ArrayList<>();
        while (rs.next()) {
            Tutoria t = new Tutoria();
            t.setId(rs.getLong("id"));
            t.setTutorId(rs.getLong("tutor_id"));
            t.setEstudianteId(rs.getLong("estudiante_id"));
            t.setMateriaId(rs.getLong("materia_id"));
            t.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            t.setEstado(rs.getString("estado"));
            tutorias.add(t);
        }
        rs.close();
        stmt.close();
        return tutorias;
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM tutorias WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public Tutoria updateEstado(Long id, String nuevoEstado) throws SQLException {
        String sql = "UPDATE tutorias SET estado = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nuevoEstado);
        stmt.setLong(2, id);
        stmt.executeUpdate();
        stmt.close();

        // Devolver la tutoria actualizada (simplificado)
        // Puedes hacer SELECT para devolver datos más completos
        Tutoria t = new Tutoria();
        t.setId(id);
        t.setEstado(nuevoEstado);
        return t;
    }
}
