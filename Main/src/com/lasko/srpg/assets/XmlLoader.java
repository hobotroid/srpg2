package com.lasko.srpg.assets;

import java.io.IOException;
import java.util.StringTokenizer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XmlLoader extends SynchronousAssetLoader<Element, XmlLoader.Parameters> {

    public static class Parameters extends AssetLoaderParameters<Element> {

    }

    private XmlReader xmlReader = new XmlReader();
    private Element root;

    public XmlLoader() {
        super(new InternalFileHandleResolver());
    }

    public XmlLoader (FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Element load (AssetManager assetManager, String fileName, Parameters parameter) {
        FileHandle tideFile = resolve(fileName);
        try {
            return root;
        } catch (Exception e) {
            throw new GdxRuntimeException("Couldn't load xml '" + fileName + "'", e);
        }
    }

    @Override
    public Array<AssetDescriptor> getDependencies (String fileName, Parameters parameter) {
        Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
        try {
            FileHandle xmlFile = resolve(fileName);
            root = xmlReader.parse(xmlFile);

            return dependencies;
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't load xml '" + fileName + "'", e);
        }
    }

    private static FileHandle getRelativeFileHandle(FileHandle file, String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, "\\/");
        FileHandle result = file.parent();
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.equals(".."))
                result = result.parent();
            else {
                result = result.child(token);
            }
        }
        return result;
    }

}