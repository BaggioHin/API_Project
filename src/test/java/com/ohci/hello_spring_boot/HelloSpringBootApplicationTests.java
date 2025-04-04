

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductRecommendationServiceTest {

	@Mock
	private AIModelClient aiModelClient;

	@InjectMocks
	private ProductRecommendationService productRecommendationService;

	@Test
	void recommendProducts_ShouldReturnRecommendations_WhenValidSoldProducts() {
		// Given
		List<String> soldProducts = Arrays.asList("Laptop", "Chuột không dây");
		List<String> expectedRecommendations = Arrays.asList("Bàn phím cơ", "Tai nghe gaming");

		// Khi gọi API mô phỏng, trả về danh sách sản phẩm gợi ý
		Mockito.when(aiModelClient.getRecommendations(soldProducts)).thenReturn(expectedRecommendations);

		// When
		List<String> recommendations = productRecommendationService.recommendProducts(soldProducts);

		// Then
		assertEquals(expectedRecommendations, recommendations);
		Mockito.verify(aiModelClient, Mockito.times(1)).getRecommendations(soldProducts);
	}

	@Test
	void recommendProducts_ShouldThrowException_WhenSoldProductsListIsEmpty() {
		// Given
		List<String> soldProducts = new ArrayList<>();

		// When & Then
		assertThrows(IllegalArgumentException.class, () -> {
			productRecommendationService.recommendProducts(soldProducts);
		});
	}
}
