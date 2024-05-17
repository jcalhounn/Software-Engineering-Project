import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

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
					Iterator<Integer> list = getFileBasedIterator(fileConfig.getFileName(), fileConfig.getDelimiter());

                    for (Iterator<Integer> it = list; it.hasNext(); ) {
                        int i = it.next();
                        System.out.print(i + " ");
                    }

					return list;

				}
			};
		});
    }

	private Iterator<Integer> getFileBasedIterator(String fileName, char delimiter) {
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));

			return new Iterator<Integer>() {
				String line;
				StringTokenizer tokenizer;
				int nextInt = -1;

				private boolean readNextInt() throws IOException {
					while ((line = buff.readLine()) != null) {
						tokenizer = new StringTokenizer(line, String.valueOf(delimiter));
						if (tokenizer.hasMoreTokens()) {
							nextInt = Integer.parseInt(tokenizer.nextToken().trim());
							return true;
						}
					}
					return false;
				}

				{
					readNextInt();
				}

				@Override
				public boolean hasNext() {
					if (nextInt != -1) {
						return true;
					} else {
						try {
							return readNextInt();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}

				@Override
				public Integer next() {
					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					int result = nextInt;
					nextInt = -1;
					return result;
				}
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {
		OutputConfig.visitOutputConfig(output, config -> {
			if(delimiter=='\\'){
				writeToFile(config.getFileName(),result);
				writeToFile(config.getFileName(),'\n'+"");
			}
			else {
				writeToFile(config.getFileName(), result + delimiter);
			}
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
			if(line.equals("\\n")){
				writer.write("\r\n");
				writer.append(line);
			}
			else {
				writer.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
