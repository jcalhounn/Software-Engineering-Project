import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

//done

public class DataAPIImpl implements DataAPI  {

    @Override
    public Iterable<Integer> read(InputConfig input) {
		// Use the visitor pattern so that we can easily and safely add additional config types in the future
		return InputConfig.visitInputConfig(input, fileConfig -> {
			System.out.println( fileConfig.getDelimiter() + " " + "name:"+ fileConfig.getFileName());

			// Iterables are more convenient for method callers than iterators, so wrap our file-based iterator before returning
			return new Iterable<Integer>() {
				@Override
				public Iterator<Integer> iterator() {
					System.out.println( fileConfig.getDelimiter() + " " + "name:"+ fileConfig.getFileName());
					return getFileBasedIterator(fileConfig.getFileName(), fileConfig.getDelimiter());

				}
			};
		});
    }

    private Iterator<Integer> getFileBasedIterator(String fileName, char delimiter) {
		try {
			return new Iterator<Integer>() {

				BufferedReader buff = new BufferedReader(new FileReader(fileName));
				int nextInt = -1;
				boolean closed = false;


				private boolean readNextInt() throws IOException {
					System.out.println("read:" + buff.read());
					System.out.println("read:" + buff.read());
					StringBuilder sb = new StringBuilder();
					int nextChar;

					while ((nextChar = buff.read()) != -1) {
						if ((char) nextChar == delimiter || (char) nextChar == ' ') {
							if (!sb.isEmpty()) {
								nextInt = Integer.parseInt(sb.toString());
								return true;
							}
						} else if (Character.isDigit(nextChar)) {
							sb.append((char) nextChar);
						} else {

							throw new RuntimeException("Invalid character in file: " + (char) nextChar);
						}

					}
					if (!sb.isEmpty()) {
						nextInt = Integer.parseInt(sb.toString());
						return true;
					}
					return false;
				}

				{
					if (!readNextInt()) {
						buff.close();
						closed = true;
					}
				}

				@Override
				public Integer next() {
					int result = nextInt;
					try {
						if (!readNextInt()) {
							buff.close();
							closed = true;
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					return result;
				}

				@Override
				public boolean hasNext() {
					return nextInt != -1;
				}

            /*public void finalize() {
                if (!closed) {
                    try {
                        buff.close();
                        closed = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }*/
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {
		OutputConfig.visitOutputConfig(output, config -> {
			writeToFile(config.getFileName(), result + delimiter);
		});
		
		/* 
		 * Using lambda syntax to create an instance of WriteResult. This is an alternative to the ComputeResult approach of providing
		 * constants for success/failure.
		 */
		return () -> DataWriteResult.WriteResultStatus.SUCCESS; 
	}

	private void writeToFile(String fileName, String line) {
		// use try-with-resources syntax to automatically close the file writer
		// use the append-friendly version of the constructor
		try (FileWriter writer = new FileWriter(new File(fileName), true)) {
			writer.append(line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
