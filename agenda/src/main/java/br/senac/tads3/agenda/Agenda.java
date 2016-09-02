/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads3.agenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.tsuda
 */
public class Agenda extends ConexaoBD {

    private static Scanner entrada = new Scanner(System.in);
    static String nome, strDataNasc, email, telefone;

    public void incluir() {

        System.out.print("Digite o nome completo do contato: ");
        nome = entrada.nextLine();

        System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
        strDataNasc = entrada.nextLine();

        System.out.print("Digite o e-mail");
        email = entrada.nextLine();

        System.out.print("Digite o telefone no formato 99 99999-9999");
        telefone = entrada.nextLine();

        // 1) Abrir conexao
        PreparedStatement stmt = null;
        Connection conn = null;

        String sql = "INSERT INTO TB_CONTATO (NM_CONTATO, DT_NASCIMENTO, "
                + "VL_TELEFONE, VL_EMAIL, DT_CADASTRO) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNasc = null;
            try {
                dataNasc = formatador.parse(strDataNasc);
            } catch (ParseException ex) {
                System.out.println("Data de nascimento inválida.");
                return;
            }
            stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));

            // 2) Executar SQL
            stmt.executeUpdate();
            System.out.println("Contato cadastrado com sucesso");

        } catch (SQLException e) {
            System.out.println("Não foi possível executar.1");
        } catch (ClassNotFoundException e) {
            System.out.println("Não foi possível executar.2");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar stmt.");
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar conn.");
                }
            }
        }

        //3) Fechar conexao
    }

    public void pesquisa() {
        PreparedStatement stmt = null;
        Connection conn = null;

        String sql = "SELECT *FROM TB_CONTATO ";

    }

    public void apagar(int id) {
        PreparedStatement stmt = null;
        Connection conn;

        try {
            stmt.executeUpdate("DELETE FROM CLIENTE WHERE ID_CONTATO = '" + id + "';");
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar stmt.");
        }

    }

    public void atualizar(String dados, int n, int id) {

        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            if (n == 1) {
                String query = "UPDATE TB_CONTATO SET NM_CONTATO = '" + nome + "', NM_CONTATO = '" + dados + "' where ID_CONTATO = '" + id + "';";

                System.out.println(" Nome atualizado!");
                stmt.executeUpdate(query); //com é a mesma variavel que esta guardando a String
            } else if (n == 2) {

                String query = "UPDATE TB_CONTATO SET DT_NASCIMENTO = '" + strDataNasc + "', DT_NASCIMENTO = '" + dados + "' where ID_CONTATO = '" + id + "';";

                System.out.println(" Data de nascimento Atualizado!");
                stmt.executeUpdate(query);
            } else if (n == 3) {

                String query = "UPDATE TB_CONTATO SET VL_TELEFONE = '" + telefone + "', VL_TELEFONE = '" + dados + "' where ID_CONTATO = '" + id + "';";

                System.out.println(" Telefone Atualizado!");
                stmt.executeUpdate(query);
            } else if (n == 4) {
                String query = "UPDATE TB_CONTATO SET VL_EMAIL  = '" + email + "', VL_EMAIL = '" + dados + "' where ID_CONTATO = '" + id + "';";

                System.out.println(" E-mail Atualizado!");
                stmt.executeUpdate(query);
            }

        } catch (SQLException e) {

        }
    }

    public static void main(String[] args) {
        Agenda instancia = new Agenda();

        do {
            System.out.println("***** DIGITE UMA OPÇÃO *****");
            System.out.println("(1) Listar contatos");
            System.out.println("(2) Incluir novo contato");
            System.out.println("(3) Deletar contato");
            System.out.println("(4) Atualizar contato");
            System.out.println("(9) Sair");
            System.out.print("Opção: ");

            String strOpcao = entrada.nextLine();
            int opcao = Integer.parseInt(strOpcao);
            switch (opcao) {
                case 1:
                    instancia.pesquisa();
                    break;
                case 2:
                    instancia.incluir();
                    break;
                case 3:
                    System.out.println(" Informe o ID do usuario que deseja apagar ");
                    instancia.pesquisa();
                    int id = entrada.nextInt();
                    instancia.apagar(id);
                    break;
                case 4:

                    System.out.println(" Informe o codigo do usuario deseja editar ");
                    instancia.pesquisa();
                    int editarUsuario = entrada.nextInt();

                    System.out.println(" Informe qual dado deseja alterar ");
                    System.out.println(" 1 - Nome ");
                    System.out.println(" 2 - Data de nascimento ");
                    System.out.println(" 3 - E-mail ");
                    System.out.println(" 4 - Telefone ");

                    int opcaoEditar = entrada.nextInt();

                    switch (opcaoEditar) {

                        case 1:
                            System.out.println(" Digite o novo nome: ");
                            String nomeNovo = entrada.nextLine();
                            instancia.atualizar(nomeNovo, opcaoEditar, editarUsuario);

                            break;
                        case 2:
                            System.out.println(" Digite a nova Data de nascimento: ");

                            String strNovaData = entrada.nextLine();
                            instancia.atualizar(strNovaData, opcaoEditar, editarUsuario);
                            break;
                        case 3:
                            System.out.println(" Digite o novo e-mail: ");
                            String emailNovo = entrada.nextLine();
                            instancia.atualizar(emailNovo, opcaoEditar, editarUsuario);
                            break;
                        case 4:
                            System.out.println(" Digite o novo telefone: ");
                            String telNovo = entrada.nextLine();
                            instancia.atualizar(telNovo, opcaoEditar, editarUsuario);
                            break;
                        default:
                            System.out.println(" opção invalida!! ");

                    }

                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA");
            }

        } while (true);

    }

}
