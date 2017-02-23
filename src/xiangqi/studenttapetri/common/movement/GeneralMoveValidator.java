package xiangqi.studenttapetri.common.movement;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenttapetri.common.XiangqiBoard;
import xiangqi.studenttapetri.common.XiangqiCoordinateImpl;

/**
 * This class contains the logic used for verifying that a General piece moves correctly.
 * 
 * @author Tim Petri
 * @version Feb 20, 2017
 */
public class GeneralMoveValidator implements MoveValidator
{
	private XiangqiBoard board;

	public GeneralMoveValidator(XiangqiBoard board)
	{
		this.board = board;
	}

	/* 
	 * @see xiangqi.studenttapetri.common.MoveValidator#isValid(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public boolean isValid(XiangqiCoordinate source, XiangqiCoordinate destination, XiangqiPiece piece)
	{
		final XiangqiColor ownColor = piece.getColor();
		
		// cannot capture own piece
		if (ownColor == board.getPieceAt(destination, ownColor).getColor()) return false;
		
		// test for flying general case
		if (board.getPieceAt(destination, ownColor).getPieceType() == XiangqiPieceType.GENERAL
				 && source.getFile() == destination.getFile()) {
			
			// check that there is no piece in between source and destination
			for (int i = source.getRank() + 1; i < destination.getRank(); i++) {
				if (board.getPieceAt(XiangqiCoordinateImpl.makeCoordinate(i, source.getFile()), 
						piece.getColor()).getPieceType() != XiangqiPieceType.NONE)
					return false;
			}
			
			return true;
		}
		
		// may not leave palace
		// TOOD: be smarter about this (board.getPalaceBoundaries()) make sure its in them
		if (destination.getRank() > 3 || destination.getFile() < 4 || destination.getFile() > 6) {
			return false;
		}
		
		// move may only be of length 1
		return ( (destination.getRank() == source.getRank() && Math.abs(source.getFile() - destination.getFile()) == 1) 
				|| (destination.getFile() == source.getFile() && Math.abs(source.getRank() - destination.getRank()) == 1));
	}

}
