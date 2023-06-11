package com.card.deck.api.dto;

import java.util.Map;

public record CardResponseExternalApiDTO(String code, String image, Map<String, String> images, String value, String suit) {

}
