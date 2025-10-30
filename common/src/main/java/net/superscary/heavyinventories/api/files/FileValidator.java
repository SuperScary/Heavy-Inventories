package net.superscary.heavyinventories.api.files;

import net.minecraft.client.Minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileValidator {

    private static final String SUBFOLDER = "weights";
    private static final String FILE_EXTENSION = ".json";

    public static Path validate(String fileName) {
        Path gameDir = Minecraft.getInstance().gameDirectory.toPath();
        Path weightDir = gameDir.resolve(SUBFOLDER);

        Path path = Paths.get(fileName);

        if (path.isAbsolute() && path.startsWith(weightDir)) {
            if (!fileName.endsWith(FILE_EXTENSION)) {
                path = path.resolveSibling(fileName + FILE_EXTENSION);
            }

            return path;
        }

        if (fileName.contains(SUBFOLDER + "/") || fileName.contains(SUBFOLDER + "\\")) {
            Path resolved = gameDir.resolve(fileName);
            if (!resolved.toString().endsWith(FILE_EXTENSION)) {
                resolved = resolved.resolveSibling(resolved.getFileName().toString() + FILE_EXTENSION);
            }

            return resolved.normalize();
        }

        String name = fileName.endsWith(FILE_EXTENSION) ? fileName : fileName + FILE_EXTENSION;
        return weightDir.resolve(name).normalize();
    }

}
