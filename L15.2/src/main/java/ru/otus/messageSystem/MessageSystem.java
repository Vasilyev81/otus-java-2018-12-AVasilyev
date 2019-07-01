package ru.otus.messageSystem;

import ru.otus.messageSystem.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("LoopStatementThatDoesntLoop")
public final class MessageSystem {
	private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
	private final List<Thread> workers;
	private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
	private final Map<Address, Addressee> addresseeMap;

	public MessageSystem(MessageSystemContext context) {
		context.setMessageSystem(this);
		workers = new ArrayList<>();
		messagesMap = new HashMap<>();
		this.addresseeMap = context.getAddresseeMap();
		messagesMapInit(addresseeMap);
	}
	private void messagesMapInit(Map<Address, Addressee> addresseeMap) {
		for (Address address : addresseeMap.keySet()){
			messagesMap.put(address, new LinkedBlockingQueue<>());
		}
	}

	public void sendMessage(Message message) {
		messagesMap.get( message.getTo() ).add(message);
	}

	public void start() {
		int size = addresseeMap.size();
		System.out.println("Message system start(), addresseeMap.size() = " + size);
		for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
			String name = "MS-worker-" + entry.getKey().getId();
			Thread thread = new Thread(() -> {
				while (true) {
					LinkedBlockingQueue<Message> queue = messagesMap.get(entry.getKey());
					while (true) {
						try {
							Message message = queue.take();
							message.exec(entry.getValue());
						} catch (InterruptedException e) {
							logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
							return;
						}
					}
				}
			});
			thread.setName(name);
			thread.start();
			logger.info("Thread: [" + thread.getName() + "] started.");
			workers.add(thread);
		}
	}

	public void dispose() {
		workers.forEach(Thread::interrupt);
	}
}
