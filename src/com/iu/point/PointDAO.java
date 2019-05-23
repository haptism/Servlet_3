package com.iu.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.util.DBConnector;

public class PointDAO {
	
	//메서드명 getTotalCount
	public int getTotalCount(String kind, String search)throws Exception{
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="select count(num) from point where "+kind+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return result;
	}
	
	//메서드명은 selectList,리턴 ArrayList 매개변수 x 예외는 던지기
	public ArrayList<PointDTO> selectList(String kind, String search, int startRow, int lastRow) throws Exception{
		ArrayList<PointDTO> ar = new ArrayList<PointDTO>();
		
		Connection con = DBConnector.getConnect();
		String sql ="select * from "
				+ "(select rownum R, P.* from "
				+ "(select * from point where "+kind+" like ? order by num desc) P) "
				+ "where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		st.setInt(2, startRow);
		st.setInt(3, lastRow);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			PointDTO pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setTotal(rs.getInt("total"));
			pointDTO.setAvg(rs.getDouble("avg"));
			ar.add(pointDTO);
		}
		
		DBConnector.disConnect(rs, st, con);
		
		return ar;
	}
	
	
	//메서드명은 selectOne, 리턴 PointDTO 매개변수 int 예외는 던지기
	public PointDTO selectOne(int num) throws Exception{
		PointDTO pointDTO= null;
		Connection con = DBConnector.getConnect();
		String sql ="select * from point where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setTotal(rs.getInt("total"));
			pointDTO.setAvg(rs.getDouble("avg"));
		}
		
		DBConnector.disConnect(rs, st, con);
		return pointDTO;
	}
	
	
	
	
	//메서드명은 delete, 리턴은 int, 매개변수 int 예외는 던지기
	public int delete(int num) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="delete point where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
		
	}
	
	//메서드명은 update, 리턴은 int , 매개변수 PointDTO 예외는 던지기
	public int update(PointDTO pointDTO) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql ="update point set name=?, kor=?, eng=?, math=?, total=?, avg=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getTotal());
		st.setDouble(6, pointDTO.getAvg());
		st.setInt(7, pointDTO.getNum());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	//메서드명은 insert ,리턴은 int, 매개변수 PointDTO 예외는 던지기.
	public int insert(PointDTO pointDTO) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql="insert into point values(point_seq.nextval, ?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getTotal());
		st.setDouble(6, pointDTO.getAvg());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	
	

}
