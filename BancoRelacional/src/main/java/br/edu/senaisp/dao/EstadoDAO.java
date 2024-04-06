package br.edu.senaisp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.senaisp.model.Cidade;
import br.edu.senaisp.model.Estado;

public class EstadoDAO {

	private final String SQLINSERT = "INSERT INTO estado (uf, nome) VALUES(?, ?)";

	public int novo(Estado estado) {
		int id = -1;
		try {
			Connection con = dao.conexao();

			if (!con.isClosed()) {
				PreparedStatement ps = con.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, estado.uf);
				ps.setString(2, estado.nome);

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

	public int novoCompleto(Estado estado) {
		int id = 0;
		Connection con = null;
		try {
			con = dao.conexao();

			con.setAutoCommit(false);
			if (!con.isClosed()) {
				PreparedStatement ps = con.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, estado.uf);
				ps.setString(2, estado.nome);
				ps.execute();

				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				id = rs.getInt(1);

				estado.id = id;

				CidadeDAO cDAO = new CidadeDAO();
				for (Cidade cidade : estado.cidades) {
					cidade.estado = estado;
					cDAO.novo(cidade, con);
				}
				con.commit();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				con.rollback();

			} catch (SQLException e1) {

			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return id;

	}

}
