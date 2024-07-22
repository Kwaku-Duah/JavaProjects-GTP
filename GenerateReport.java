import org.jacoco.cli.internal.Main;

public class GenerateReport {
    public static void main(String[] args) throws Exception {
        String[] cliArgs = { "report", "/home/kduah/Desktop/libraryManagement/libraryManagement/jacoco.exec",
                "--classfiles", "/home/kduah/Desktop/libraryManagement/libraryManagement/bin", "--sourcefiles",
                "/home/kduah/Desktop/libraryManagement/libraryManagement/src", "--html", "coverage-report" };
        Main.main(cliArgs);
    }
}
