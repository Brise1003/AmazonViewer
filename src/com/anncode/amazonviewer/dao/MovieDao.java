package com.anncode.amazonviewer.dao;

import com.anncode.amazonviewer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;
import static com.anncode.amazonviewer.db.DataBase.*;

import java.sql.*;
import java.util.ArrayList;

public interface MovieDao extends IDBConnection {

    default Movie setMovieViewed(Movie movie){
        try (Connection connection = connectToDB()){

            Statement statement = connection.createStatement();
            String query = "INSERT INTO " + TVIEWED+
                    " ("+TVIEWED_IDMATERIAL+", "+TVIEWED_IDELEMENT+", "+TVIEWED_IDUSUARIO+", "+TVIEWED_DATE+")" +
                    " VALUES("+ID_TMATERIALS[0]+", "+movie.getId()+", "+TUSER_IDUSUARIO+", NOW(3))";

            if (statement.executeUpdate(query) > 0 ){
                System.out.println("Se marcó en visto.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return movie;
    }

    default ArrayList<Movie> read(){
        ArrayList<Movie> movies = new ArrayList<>();
        try (Connection connection = connectToDB()){
            String query = "SELECT * FROM " + TMOVIE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Movie movie = new Movie(rs.getString(TMOVIE_TITLE), rs.getString(TMOVIE_GENRE), rs.getString(TMOVIE_CREATOR), Integer.parseInt(rs.getString(TMOVIE_DURATION)), Short.parseShort(rs.getString(TMOVIE_YEAR)));

                movie.setId(Integer.parseInt(rs.getString(TMOVIE_ID)));
                if(getMovieViewed(preparedStatement, connection, movie.getId())){
                    movie.setViewed(Boolean.TRUE);
                    movie.setDateTimeViewed(getDateTimeViewed(preparedStatement, connection, movie.getId()));
                } else {
                    movie.setViewed(Boolean.FALSE);
                }
                movies.add(movie);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return movies;
    }

    private boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int id_movie){
        boolean viewed = false;
        String query = "SELECT * FROM " + TVIEWED +
                " WHERE " + TVIEWED_IDMATERIAL + "= ?"+
                " AND " + TVIEWED_IDELEMENT + "= ?" +
                " AND " + TVIEWED_IDUSUARIO + "= ?";

        ResultSet rs = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID_TMATERIALS[0]);
            preparedStatement.setInt(2, id_movie);
            preparedStatement.setInt(3, TUSER_IDUSUARIO);

            rs= preparedStatement.executeQuery();
            viewed = rs.next();
        } catch (Exception e){
            e.printStackTrace();
        }
        return viewed;
    }

    private Timestamp getDateTimeViewed(PreparedStatement preparedStatement, Connection connection, int id_movie){
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM " + TVIEWED + " WHERE " + TVIEWED_IDMATERIAL + " = ? " +
                    " AND " + TVIEWED_IDELEMENT + " = ? AND " + TVIEWED_IDUSUARIO + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID_TMATERIALS[0]);
            preparedStatement.setInt(2, id_movie);
            preparedStatement.setInt(3, TUSER_IDUSUARIO);

            rs = preparedStatement.executeQuery();
            if (rs.next()){
                return Timestamp.valueOf(rs.getString(TVIEWED_DATE));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
