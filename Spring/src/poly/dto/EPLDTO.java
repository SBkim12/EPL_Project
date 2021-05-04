package poly.dto;

public class EPLDTO {
	//england team logo
	private String team_id;
	private String name;
	private String short_code;
	private String logo; //england_team_table포함
	
	//england_team_table
	private String team_name;
	private String ko_name;
	private int recent_rank;
	private int recent_won;
	private int recent_draw;
	private int recent_lost;
	private int recent_points;
	private int goals_scored;
	private int goals_against;
	private int home_win;
	private int home_draw;
	private int home_lost;
	
	//england_season
	private String season_id;
	private String season_name; //england_team_table포함
	private String start_date;
	private String end_date;
	
	//호출 URL
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSeason_id() {
		return season_id;
	}

	public void setSeason_id(String season_id) {
		this.season_id = season_id;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	
	//england team_logo
	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShort_code() {
		return short_code;
	}

	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	//england_team_table
	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getKo_name() {
		return ko_name;
	}

	public void setKo_name(String ko_name) {
		this.ko_name = ko_name;
	}

	public int getRecent_rank() {
		return recent_rank;
	}

	public void setRecent_rank(int recent_rank) {
		this.recent_rank = recent_rank;
	}

	public int getRecent_won() {
		return recent_won;
	}

	public void setRecent_won(int recent_won) {
		this.recent_won = recent_won;
	}

	public int getRecent_draw() {
		return recent_draw;
	}

	public void setRecent_draw(int recent_draw) {
		this.recent_draw = recent_draw;
	}

	public int getRecent_lost() {
		return recent_lost;
	}

	public void setRecent_lost(int recent_lost) {
		this.recent_lost = recent_lost;
	}

	public int getRecent_points() {
		return recent_points;
	}

	public void setRecent_points(int recent_points) {
		this.recent_points = recent_points;
	}

	public int getGoals_scored() {
		return goals_scored;
	}

	public void setGoals_scored(int goals_scored) {
		this.goals_scored = goals_scored;
	}

	public int getGoals_against() {
		return goals_against;
	}

	public void setGoals_against(int goals_against) {
		this.goals_against = goals_against;
	}

	public int getHome_win() {
		return home_win;
	}

	public void setHome_win(int home_win) {
		this.home_win = home_win;
	}

	public int getHome_draw() {
		return home_draw;
	}

	public void setHome_draw(int home_draw) {
		this.home_draw = home_draw;
	}

	public int getHome_lost() {
		return home_lost;
	}

	public void setHome_lost(int home_lost) {
		this.home_lost = home_lost;
	}
	

	
	
}
