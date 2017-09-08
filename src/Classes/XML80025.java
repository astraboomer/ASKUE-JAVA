package Classes;

import Classes.XmlTag.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class XML80025 extends XML80020 {
    private Peretok peretok;

    public XML80025(File file) {
        super(file);
    }

    public Peretok getPeretok() {
        return peretok;
    }

    public void setPeretok(Peretok peretok) {
        this.peretok = peretok;
    }

    @Override
    public void readMessage() {
        Message message = new Message();
        message.setMessageClass(getXmlDOMDoc().getDocumentElement().getAttributes().getNamedItem("class").getNodeValue());
        message.setVersion(getXmlDOMDoc().getDocumentElement().getAttributes().getNamedItem("version").getNodeValue());
        setMessage(message); // переменной message этого класса передаем через метод значение локал. message-а
    }

    @Override
    public void readDateTime() {
        DateTime dateTime = new DateTime();
        NodeList messageChildNodeList = getXmlDOMDoc().getDocumentElement().getChildNodes();
        int messageChildNodeCount = messageChildNodeList.getLength();
        for (int i = 0; i < messageChildNodeCount; i++) { // перебираем все дочер. узлы message-а
            if (messageChildNodeList.item(i).getNodeName().equals("datetime")) {
                NodeList dtChildNodeList = messageChildNodeList.item(i).getChildNodes();
                int dtChildNodeCount = dtChildNodeList.getLength();
                for (int j = 0; j < dtChildNodeCount; j++) { // перебираем все дочер. узлы datetime-а
                    if (dtChildNodeList.item(j).getNodeName().equals("day")) {
                        dateTime.setDay(dtChildNodeList.item(j).getTextContent());
                        break;
                    }
                }
                break; // выходим из цикла, если datetime найден
            }
        }
        setDateTime(dateTime); // переменной dateTime этого класса передаем через метод значение локал. dateTime-а
    }

    @Override
    public void readAreaList () {
        Sender sender = new Sender();
        NodeList messageChildNodeList = getXmlDOMDoc().getDocumentElement().getChildNodes();
        int messageChildNodeCount = messageChildNodeList.getLength();
        for (int i = 0; i < messageChildNodeCount; i++) { // перебираем все дочер. узлы message-а
            if (messageChildNodeList.item(i).getNodeName().equals("peretok")) {
                peretok = new Peretok();
                peretok.setCodeFrom(messageChildNodeList.item(i).getAttributes().getNamedItem("code-from").
                        getNodeValue());
                peretok.setCodeTo(messageChildNodeList.item(i).getAttributes().getNamedItem("code-to").
                        getNodeValue());
                peretok.setName(messageChildNodeList.item(i).getAttributes().getNamedItem("name").
                        getNodeValue());
                NodeList peretokChildNodeList = messageChildNodeList.item(i).getChildNodes();
                int peretokChildNodeCount = peretokChildNodeList.getLength();
                for (int j = 0; j < peretokChildNodeCount; j++) { // перебираем все дочер. узлы peretok-а
                    if (peretokChildNodeList.item(j).getNodeName().equals("sender")) {
                        sender.setInn(peretokChildNodeList.item(j).getAttributes().getNamedItem("inn").getNodeValue());
                        sender.setName(peretokChildNodeList.item(j).getAttributes().getNamedItem("name").getNodeValue());
                        this.setSender(sender); // переменной этого класса sender передаем через метод значение
                                                // локал. sender-а
                        NodeList senderNodeList = peretokChildNodeList.item(j).getChildNodes();
                        for (int k = 0; k < senderNodeList.getLength(); k++) {
                            if (senderNodeList.item(k).getNodeName().equals("aiis")) {
                                Area area = new Area();
                                area.setInn(senderNodeList.item(k).getAttributes().getNamedItem("aiiscode").
                                        getNodeValue());
                                area.setName(senderNodeList.item(k).getAttributes().getNamedItem("name").
                                        getNodeValue());
                                Node aiisNode = senderNodeList.item(k);
                                for (int l = 0; l < aiisNode.getChildNodes().getLength(); l++) {
                                    // получаем элемент measPointList-а по узлу measuringpoint
                                    MeasuringPoint measuringPoint = readMeasurePoint(aiisNode.getChildNodes().item(l));
                                    area.addMeasPoint(measuringPoint);
                                }
                                this.addArea(area);
                            }
                        }
                        break;
                    }
                }
                break; // выходим из цикла, если peretok найден
            }
        }

    }

    @Override
    public void readSender() {

    }

}
