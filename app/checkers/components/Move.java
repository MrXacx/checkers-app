package app.checkers.components;

public class Move{
	public static int[][] getPossibleMoves(int line, int column){
		List<int[]> moves = Arrays.toList({line-1, column-1}, {line-1, column+1}, {line+1, column-1}, {line+1, column+1});
		
		moves.forEach(position -> {
			if((position[0] < 0 || position[0] > 7) || (position[1] < 0 || position[1] > 7)){
				moves.remove(position);
			}
		});
		
		return moves.toArray(new int);
		
	}
}
