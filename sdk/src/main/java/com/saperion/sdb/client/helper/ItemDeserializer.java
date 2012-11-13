package com.saperion.sdb.client.helper;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;

public class ItemDeserializer extends StdDeserializer<ClientModel<?>> {
	
	public ItemDeserializer() {
		super(ClientModel.class);
	}
	
	protected ItemDeserializer(Class<ClientModel<?>> vc) {
		super(vc);
	}

	@Override
	public ClientModel<?> deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDeserializationConfig(ctxt.getConfig());
		
		JsonNode node = jp.readValueAsTree();
		String type = node.findValue("type").asText();
		if("folder".equals(type)){
			return new Folder(mapper.readValue(node, com.saperion.sdb.rs.models.Folder.class), null);
		}else if("document".equals(type)){
			return new Document(mapper.readValue(node, com.saperion.sdb.rs.models.Document.class), null);
		}else{
			throw new IOException("Unknown type: "+type);
		}
	}
}
