package br.edu.senaisp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.senaisp.model.Cidade;

public class CidadeDAO {

	private final String SQLINSERT = "INSERT INTO cidade (cidade, id_estado) VALUES(?, ?)";

	public int novo(Cidade cidade) {
		int id = -1;
		try {
			Connection con = dao.conexao();

			if (!con.isClosed()) {
				PreparedStatement ps = con.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, cidade.nome);
				ps.setInt(2, cidade.estado.id);

				ps.execute();

				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return id;

	}

	public int novo(Cidade cidade, Connection con) throws SQLException {
		int id = -1;

		if (!con.isClosed()) {
			PreparedStatement ps = con.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, cidade.nome);
			ps.setInt(2, cidade.estado.id);

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		}
		return id;
	}
}