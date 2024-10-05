package com.websocket.chatting.controller;

//import com.websocket.chatting.dto.ChatRoom;
//import com.websocket.chatting.repo.ChatRoomRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/chat")
//public class ChatRoomController {
//    private final ChatRoomRepository chatRoomRepository;
//
//    @GetMapping("/room")
//    public String rooms(Model model) {
//        log.info("채팅방 리스트 페이지 접근됨");
//        return "chat/room";
//    }
//
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();
//        log.info("모든 채팅방을 검색했습니다 : {}", rooms);
//        return chatRoomRepository.findAllRoom();
//    }
//
//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam String name) {
//        ChatRoom newRoom = chatRoomRepository.createChatRoom(name);
//        log.info("새로운 채팅방을 생성했습니다 : {}", newRoom);
//        return chatRoomRepository.createChatRoom(name);
//    }
//
//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        log.info("채팅방에 들어왔습니다. : {}", roomId);
//        model.addAttribute("roomId", roomId);
//        return "chat/roomdetail";
//    }
//
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        ChatRoom room = chatRoomRepository.findRoomById(roomId);
//        log.info("방번호에 대한 정보를 검색했습니다 : {} -> {}", roomId, room);
//        return chatRoomRepository.findRoomById(roomId);
//    }
//}

import com.websocket.chatting.dto.ChatRoom;
import com.websocket.chatting.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}