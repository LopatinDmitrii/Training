package lopatin.handler;

import lombok.extern.slf4j.Slf4j;
import lopatin.util.FileParser;
import lopatin.util.FileWrite;
import lopatin.util.InvalidCommandException;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
public class Add implements CommandHandler {
    Pattern pattern;

    @Override
    public void handle(String command) throws InvalidCommandException {
        pattern = Pattern.compile("[Aa]dd *[\\d]{0,3} [\\w]+.txt *\"[ \\w\\d]*\"");
        if (!pattern.matcher(command).matches()) {
            log.error("Команда add введена неверно");
            throw new InvalidCommandException("Команда add введена неверно");
        }
        Scanner scanner = new Scanner(command);
        scanner.next();
        int lineNumber = 0;
        boolean hasLineNumber = scanner.hasNextInt();
        if (hasLineNumber) {
            lineNumber = scanner.nextInt();
        }
        String fileName = scanner.next();
        String text = scanner.findInLine("\"[\\w ]*\"");
        text = text.replace("\"", "");
        List<String> lineList = FileParser.parseToLines(fileName);
        if (hasLineNumber) {
            for (int i = lineList.size(); i < lineNumber - 1; i++) {
                lineList.add("");
            }
            lineList.add(lineNumber - 1, text);
        } else {
            lineList.add(text);
        }
        FileWrite.writeLines(fileName, lineList);
    }
}
