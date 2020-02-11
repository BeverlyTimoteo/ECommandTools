package ecommandtools.utils;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileChoose extends JFileChooser {

    public FileChoose(String[] extension) {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);

        this.addChoosableFileFilter(new Filtro(extension));
    }

    public File openDialog(Component comp) {
        if (this.showOpenDialog(comp) == JFileChooser.APPROVE_OPTION) {
            return this.getSelectedFile();
        }

        return null;
    }

    class Filtro extends FileFilter {

        String[] filtro;

        public Filtro(String[] filtro) {
            this.filtro = filtro;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = new ExtensionFile().getExtension(f);

            if (extension != null) {
                return (Arrays.asList(filtro).contains(extension));
            }

            return false;
        }

        @Override
        public String getDescription() {
            return Arrays.toString(filtro).replace("[", "").replace("]", "");
        }
    }

}
