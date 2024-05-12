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

			// Iterables are more convenient for method callers than iterators, so wrap our file-based iterator before returning
			return new Iterable<Integer>() {
				@Override
				public Iterator<Integer> iterator() {

					return getFileBasedIterator(fileConfig.getFileName(), fileConfig.getDelimiter());

				}
			};
		});
    }

    private Iterator<Integer> getFileBasedIterator(String fileName, char delimiter) {
		try {
			return new Iterator<Integer>() {

				final char RETURN = '\r';
				final char NEWLINE = '\n';

				BufferedReader buff = new BufferedReader(new FileReader(fileName));
				int nextInt = -1;
				boolean closed = false;


				private boolean readNextInt() throws IOException {

					StringBuilder sb = new StringBuilder();
					int nextChar;

					while ((nextChar = buff.read()) != -1) {
						System.out.println(nextChar==delimiter);
						System.out.println("Next char: " + nextChar);
						if ((char) nextChar == RETURN || (char)nextChar == NEWLINE ) {
							continue; // Skip over carriage return
						}
						if ((char) nextChar == delimiter) {
							if (!sb.isEmpty()) {
								nextInt = Integer.parseInt(sb.toString());
								System.out.println( "nextInt" + nextInt);
								return true;
							}
						} else if (Character.isDigit(nextChar)) {
							sb.append((char) nextChar);
							System.out.println( "isDigit:" + Integer.parseInt(sb.toString()));

						} else {

							throw new RuntimeException("Invalid character in file: " + (char) nextChar);
						}

					}
					if (!sb.isEmpty()) {
						nextInt = Integer.parseInt(sb.toString());
						System.out.println( "nextInt" + nextInt);
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
					if (closed) {
						return -1; // Indicates end of iterator
					}
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
