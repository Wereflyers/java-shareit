package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    /**
     * Вывод списка всех вещей, которые принадлежат конкретному пользователю.
     *
     * @param userId - идентификатор пользователя
     * @return List
     */
    List<Item> findAllByOwnerId(long userId);

    /**
     * Поиск вещей, доступных для бронирования, по названию и описанию.
     *
     * @param req - текст поиска
     * @return List
     */
    @Query("select i from Item i " +
            "where (upper(i.name)  like upper(concat('%', ?1, '%')) " +
            "or upper(i.description) like upper(concat('%', ?1, '%'))) " +
            "and i.available = true ")
    List<Item> search(String req);

}
