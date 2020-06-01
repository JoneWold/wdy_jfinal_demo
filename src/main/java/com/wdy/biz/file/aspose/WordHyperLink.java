package com.wdy.biz.file.aspose;

import com.aspose.words.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * @author wgch
 * @Description 文档超链接 aspose
 * @date 2020/5/28
 */
public class WordHyperLink implements IReplacingCallback {

    private Run splitRun(Run run, int position) {
        Run afterRun = (Run) run.deepClone(true);
        afterRun.setText(run.getText().substring(position));
        run.setText(run.getText().substring(0, position));
        run.getParentNode().insertAfter(afterRun, run);
        return afterRun;
    }

    @Override
    public int replacing(ReplacingArgs args) {
        Node currentNode = args.getMatchNode();
        boolean flag = currentNode.getPreviousSibling() != null
                && currentNode.getPreviousSibling().getNodeType() == NodeType.FIELD_SEPARATOR
                && currentNode.getNextSibling() != null
                && currentNode.getNextSibling().getNodeType() == NodeType.FIELD_END;
        int result;
        if (flag) {
            result = ReplaceAction.SKIP;
        } else {
            boolean flag2 = args.getMatchOffset() > 0;
            if (flag2) {
                currentNode = this.splitRun((Run) currentNode, args.getMatchOffset());
            }
            List<Node> runs = new ArrayList<Node>();
            DocumentBuilder builder = new DocumentBuilder(document);
            int remainingLength = args.getMatch().group().length();
            while (remainingLength > 0 && currentNode != null && currentNode.getText().length() <= remainingLength) {
                runs.add(currentNode);
                remainingLength -= currentNode.getText().length();
                do {
                    currentNode = currentNode.getNextSibling();
                }
                while (currentNode != null && currentNode.getNodeType() != NodeType.RUN);
            }
            boolean flag3 = currentNode != null && remainingLength > 0;
            if (flag3) {
                this.splitRun((Run) currentNode, remainingLength);
                runs.add(currentNode);
            }
            builder.moveTo(runs.get(0));
            builder.getFont().setColor(Color.BLUE);
            builder.getFont().setUnderline(Underline.SINGLE);
            builder.insertHyperlink(this.strKey, this.strLink, false);
            runs.stream().map(node -> (Run) node).forEach(Node::remove);
            result = ReplaceAction.SKIP;
        }
        return result;
    }


    // Token: 0x04000272 RID: 626
    private Document document;

    // Token: 0x04000273 RID: 627
    private String strKey;

    // Token: 0x04000274 RID: 628
    private String strLink;

    public WordHyperLink() {

    }

    public WordHyperLink(Document document, String strKey, String strLink) {
        this.document = document;
        this.strKey = strKey;
        this.strLink = strLink;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getStrKey() {
        return strKey;
    }

    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public String getStrLink() {
        return strLink;
    }

    public void setStrLink(String strLink) {
        this.strLink = strLink;
    }

}
