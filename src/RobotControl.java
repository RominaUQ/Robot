import java.util.ArrayList;

class RobotControl {
	private Robot r;
	private int sourceblocklenght[];

	public RobotControl(Robot r) {
		this.r = r;
	}

	//////// **************PART A*****************/////////////
	///////// *************************************/////////////
///// PLEASE REMOVE A from the following Method to run part A
	public void controlA(int barHeights[], int blockHeights[], int required[], boolean ordered) {
		for (int j = 1; j < 5; j++) {

			/// Move bars up
			for (int i = 0; i < (j == 1 ? 10 : 13 - (j * 2)); i++) {
				r.up();
			}

			/// Extend
			for (int i = 0; i < 9; i++) {
				r.extend();
			}

			///// go low
			for (int i = 0; i < (j == 1 ? 3 : (j * 2) + 1); i++) {
				r.lower();
			}

			r.pick();

			for (int i = 0; i < (j == 1 ? 3 : (j * 2) + 1); i++) {
				r.raise();

			}

			//// extend back
			for (int i = 11; i > 2; i--) {
				r.contract();
			}

			/// put them down
			for (int i = 0; i < (j == 1 ? 9 : 11 - (j * 2)); i++) {
				r.down();
			}

			r.drop();
		}
	}

	//////// **************PART B*****************/////////////
	///////// *************************************/////////////

	// define a function that find the maximum of the bar heights
	
	private int getMaxBarHeight(int barHeights[]) {
		int max = 0;
		for (int b = 0; b < barHeights.length; b++) {
			if (max < barHeights[b]) {
				max = barHeights[b];
			}
		}

		System.out.println("Max height is: " + max);
		return max;
	}

	///// PLEASE REMOVE B from the following Method to run part B
	
	public void controlB(int barHeights[], int blockHeights[], int required[], boolean ordered) {
		for (int i = 0; i < barHeights.length; i++)
			System.out.println(barHeights[i]);
		for (int i = 0; i < blockHeights.length; i++)
			System.out.println(blockHeights[i]);

		// defining all variables we need to use in the algorithm

		int maxBarHeight = getMaxBarHeight(barHeights);
		int stackedblocks = 8;
		int movedbarHeights = 0;

		int h = 2;
		int d = 0;
		int w = 1;
		int wHeight = 1; // Height of the arm

		/// four bars to move
		for (int j = 1; j < 5; j++) {

			// Go up leveled to the highest of all blocks;
			while (h <= maxBarHeight || h <= stackedblocks) {
				r.up();
				h++;
			}

			while (w < 10) {
				r.extend();
				w++;
			}

			// Stop 1 above the stack
			while (h - wHeight - d >= stackedblocks + 1) {
				r.lower();
				d++;
			}

			// Pick up the block and reduce the stack by block size
			r.pick();
			stackedblocks -= 2;

			// Raise d until 0
			while (d > 0) {
				r.raise();
				d--;
			}

			// Go up until clearing the highest bar+ block size just picked up
			while (h - wHeight < maxBarHeight + 2 || h - wHeight < movedbarHeights + 2) {
				r.up();
				h++;
			}

			while (w > 1) {
				r.contract();
				w--;
			}

			// Go down to the top of the moved bar stack+ block currently
			// holding
			while (h - wHeight > movedbarHeights + 2) {
				r.down();
				h--;
			}

			// Drop the block, while increasing movedBarHeights with block size
			r.drop();
			movedbarHeights += 2;
		}
	}

	//////// **************PART C *****************/////////////
	///////// *************************************/////////////

	private int sumOfBlocksHeight(int blockHeights[]) {
		int sumOfBlocksHeight = 0;
		for (int i = 0; i < blockHeights.length; i++) {
			sumOfBlocksHeight = sumOfBlocksHeight + blockHeights[i];
		}
		return sumOfBlocksHeight;
	}
///// PLEASE REMOVE C from the follwoing Method to run part C
	
	public void controlC(int barHeights[], int blockHeights[], int required[], boolean ordered) {
		
		// defining all variables we need to use in the algorithm part C
		int maxBarHeight = getMaxBarHeight(barHeights);
		int stackedblocks = sumOfBlocksHeight(blockHeights);
		int movedbarHeights = 0;

		int h = 2;
		int d = 0;
		int w = 1;
		int wHeight = 1;

		/// four bars to move
		for (int j = 3; j >= 0; j--) {
			// Go up leveled to the highest of all blocks;
			while (h <= maxBarHeight || h <= stackedblocks) {
				r.up();
				h++;
			}

			// Extend the arm all the way to the end
			while (w < 10) {
				r.extend();
				w++;
			}

			// Stop the arm 1 above the stack
			while (h - wHeight - d > stackedblocks) {
				r.lower();
				d++;
			}

			// Pick up the block and reduce the stack by block size
			r.pick();
			stackedblocks -= blockHeights[j];
			System.out.println(blockHeights[j]);

			while (d > 0) {
				r.raise();
				d--;
			}

			// Go up until clearing the highest bar+ block size just picked up
			while (h - wHeight < maxBarHeight + blockHeights[j] || h - wHeight < movedbarHeights + blockHeights[j]) {
				r.up();
				h++;
			}

			while (w > 1) {
				r.contract();
				w--;
			}

			// Go down to the top of the moved bar stack+ the block currently
			// holding

			while (h - wHeight > blockHeights[j] + movedbarHeights) {
				r.down();
				h--;
			}

			// Drop the block, while increasing movedBarHeights with block size

			r.drop();
			movedbarHeights += blockHeights[j];
		}
	}

	//////// **************PART D *****************/////////////
	///////// *************************************/////////////
	
///// PLEASE REMOVE D from the Method to run part D
	
	public void control(int barHeights[], int blockHeights[], int required[], boolean ordered) {

		// defining all variables we need to use in the part D

		int maxBarHeight = getMaxBarHeight(barHeights);
		int stackedblocks = sumOfBlocksHeight(blockHeights);
		int movedbarHeights = 0;
		int tempStackedSize = 0;
		ArrayList<Integer> tempStack = new ArrayList<Integer>();
		ArrayList<Integer> sourceStack = new ArrayList<Integer>();

		for (int i = 0; i < blockHeights.length; i++) {
			sourceStack.add(blockHeights[i]);
		}

		int h = 2;
		int d = 0;
		int w = 1;
		final int wHeight = 1;
		int counter = 0;

		while (sourceStack.size() > 0 || tempStack.size() > 0) {
			// 1. check size required
			int sizeRequired = required[counter];

			// 2. define conditions to say where to pick from and where to move
			boolean pickFromSourceStack = !sourceStack.isEmpty()
					&& sourceStack.get(sourceStack.size() - 1) == sizeRequired;
			boolean pickFromTempStack = !tempStack.isEmpty() && tempStack.get(tempStack.size() - 1) == sizeRequired;
			boolean moveToSourceStack = sourceStack.isEmpty()
					|| (sourceStack.size() == 1 && sourceStack.get(0) != sizeRequired);

			// if it should pick from source stack

			if (pickFromSourceStack) {
				System.out.println("Picking from source stack");
				// Get block and move to finish

				while (h <= maxBarHeight || h <= stackedblocks || h <= tempStackedSize) {
					r.up();
					h++;
				}

				// Extend the arm all the way to the end
				while (d > 0) {
					r.raise();
					d--;
				}

				while (w < 10) {
					r.extend();
					w++;
				}

				// Stop the arm 1 above the stack
				while (h - wHeight - d > stackedblocks) {
					r.lower();
					d++;
				}

				// Pick up the block and reduce the stack by block size
				r.pick();
				int pickedUpBlock = sourceStack.get(sourceStack.size() - 1);
				stackedblocks -= pickedUpBlock;
				sourceStack.remove(sourceStack.size() - 1);
				System.out.println("Picked up: " + pickedUpBlock);

				while (d > 0) {
					r.raise();
					d--;
				}

				// Go up until clearing the highest bar+ block size just picked
				// up
				while (h - wHeight < maxBarHeight + pickedUpBlock || h - wHeight < movedbarHeights + pickedUpBlock
						|| h - wHeight < tempStackedSize + pickedUpBlock) {
					r.up();
					h++;
				}

				while (w > 1) {
					r.contract();
					w--;
				}

				while (h - wHeight > pickedUpBlock + movedbarHeights) {
					r.down();
					h--;
				}

				// Drop the block, while increasing movedBarHeights with block
				// size

				r.drop();
				movedbarHeights += pickedUpBlock;
				counter++;

				// if the block should be picked from the temporary stack

			} else if (pickFromTempStack) {
				System.out.println("Picking from temp stack");
				while (h <= maxBarHeight || h <= tempStackedSize) {
					r.up();
					h++;
				}

				while (d > 0) {
					r.raise();
					d--;
				}

				while (w < 9) {
					r.extend();
					w++;
				}

				while (w > 9) {
					r.contract();
					w--;
				}

				while (h - wHeight - d > tempStackedSize) {
					r.lower();
					d++;
				}

				// Pick up the block and reduce the stack by block size
				r.pick();
				int pickedUpSize = tempStack.get(tempStack.size() - 1);
				tempStackedSize -= pickedUpSize;
				tempStack.remove(tempStack.size() - 1);

				while (d > 0) {
					r.raise();
					d--;
				}

				while (h - wHeight < movedbarHeights + pickedUpSize || h - wHeight < maxBarHeight + pickedUpSize) {
					r.up();
					h++;
				}

				while (w > 1) {
					r.contract();
					w--;
				}

				while (h - wHeight > pickedUpSize + movedbarHeights) {
					r.down();
					h--;
				}

				r.drop();
				movedbarHeights += pickedUpSize;
				counter++;

				/// if moving from temporary back to the source

			} else if (moveToSourceStack) {
				System.out.println("Moving temp stack to source stack");
				while (d == 0
						&& (h <= maxBarHeight || h <= stackedblocks || h <= tempStackedSize || h <= movedbarHeights)) {
					r.up();
					h++;
				}

				while (d > 0) {
					r.raise();
					d--;
				}

				while (w < 9) {
					r.extend();
					w++;
				}

				while (h - wHeight - d > tempStackedSize) {
					r.lower();
					d++;
				}

				// Pick up the block and reduce the stack by block size
				r.pick();
				int pickedUpBlock = tempStack.get(tempStack.size() - 1);
				tempStackedSize -= pickedUpBlock;
				tempStack.remove(tempStack.size() - 1);
				System.out.println("Picked up: " + pickedUpBlock);

				while (d > 0) {
					r.raise();
					d--;
				}

				while (h - wHeight - d < stackedblocks + pickedUpBlock
						|| h - wHeight - d < tempStackedSize + pickedUpBlock) {
					r.up();
					h++;
				}

				while (w < 10) {
					r.extend();
					w++;
				}

				while (h - wHeight - d > pickedUpBlock + stackedblocks) {
					r.lower();
					d++;
				}

				r.drop();
				stackedblocks += pickedUpBlock;
				sourceStack.add(pickedUpBlock);

				/// the other condition is moving source stack to temp stack

			} else {
				System.out.println("Moving source stack to temp stack");
				while (d == 0
						&& (h <= maxBarHeight || h <= stackedblocks || h <= tempStackedSize || h <= movedbarHeights)) {
					r.up();
					h++;
				}

				while (d > 0) {
					r.raise();
					d--;
				}

				while (w < 10) {
					r.extend();
					w++;
				}

				while (h - wHeight - d > stackedblocks) {
					r.lower();
					d++;
				}

				// Pick up the block and reduce the stack by block size

				r.pick();
				int pickedUpBlock = sourceStack.get(sourceStack.size() - 1);
				sourceStack.remove(sourceStack.size() - 1);
				stackedblocks -= pickedUpBlock;
				System.out.println("Picked up" + pickedUpBlock);

				while (d > 0) {
					r.raise();
					d--;
				}

				// Go up until clearing the highest bar+ block size just picked
				// up
				while (h - wHeight - d < maxBarHeight + pickedUpBlock
						|| h - wHeight - d < tempStackedSize + pickedUpBlock) {
					r.up();
					h++;
				}

				while (w > 9) {
					r.contract();
					w--;
				}

				while (h - wHeight - d > pickedUpBlock + tempStackedSize) {
					r.lower();
					d++;
				}

				// Drop the block, while increasing movedBarHeights with block
				// size
				r.drop();
				tempStackedSize += pickedUpBlock;
				tempStack.add(pickedUpBlock);
			}
		}
	}
}
