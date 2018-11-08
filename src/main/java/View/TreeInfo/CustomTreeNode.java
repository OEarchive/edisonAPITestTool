package View.TreeInfo;

import com.google.gson.JsonElement;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode {

    public enum NodeType {

        ARRAY, OBJECT, VALUE
    }

    NodeType nodeType;
    String value;
    final int index;
    final String fieldName;

    public CustomTreeNode(String fieldName, int index, JsonElement elem) throws Exception {

        this.index = index;
        this.fieldName = fieldName;

        if (elem.isJsonPrimitive()) {
            this.nodeType = NodeType.VALUE;
            this.value = elem.toString();
        } else if (elem.isJsonNull()) {
            this.nodeType = NodeType.VALUE;
            this.value = elem.toString();
        } else if (elem.isJsonArray()) {
            this.nodeType = NodeType.ARRAY;
            this.value = elem.toString();
            addChildren(elem);

        } else if (elem.isJsonObject()) {
            this.nodeType = NodeType.OBJECT;
            this.value = elem.toString();
            addChildren(elem);

        } else {
            throw new Exception("oops...parsing error");
        }
       
    }
    
    

    private void addChildren(JsonElement elem) throws Exception {
        switch (this.nodeType) {
            case ARRAY:
                int index = 0;
                Iterator<JsonElement> it = elem.getAsJsonArray().iterator();
                while (it.hasNext()) {
                    JsonElement element = it.next();
                    CustomTreeNode childNode = new CustomTreeNode(null, index, element);
                    this.add(childNode);
                    index++;
                }
                break;
            case OBJECT:
                for (Entry<String, JsonElement> entry : elem.getAsJsonObject().entrySet()) {
                    CustomTreeNode childNode = new CustomTreeNode(entry.getKey(), -1, entry.getValue());
                    this.add(childNode);
                }
                break;
            default:
                throw new IllegalStateException("Internal coding error this should never happen.");
        }
    }

    @Override
    public String toString() {
        switch (this.nodeType) {
            case ARRAY:
            case OBJECT:
                if (index >= 0) {
                    return String.format("[%d] (%s)", index, this.nodeType.name());
                } else if (fieldName != null) {
                    return String.format("%s (%s)", fieldName, this.nodeType.name());
                } else {
                    return String.format("(%s)", this.nodeType.name());
                }
            default:
                if (index >= 0) {
                    return String.format("[%d] %s", index, value);
                } else if (fieldName != null) {
                    return String.format("%s: %s", fieldName, value);
                } else {
                    return String.format("%s", value);
                }

        }
    }



}
