package com.dwarfeng.statistics.impl.handler.bridge.multi;

import com.dwarfeng.statistics.impl.handler.bridge.FullBridge;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器。
 *
 * <p>
 * 多重桥接器本身不直接实现数据的桥接方法，而是通过代理的方式实现。数据写入多重桥接器时，
 * 会调用对应的代理列表，依次调用写入方法。<br>
 * 数据从多重桥接器中读取时，会使用列表中的第一个代理，作为首选代理，调用读取方法。<br>
 * 多重桥接器提供的保持器/持久器是否为只写保持器/持久器取决于首选代理对应的保持器/持久器是否为只写保持器/持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiBridge extends FullBridge {

    public static final String BRIDGE_TYPE = "multi";

    private final MultiBridgeKeeper keeper;
    private final MultiBridgePersister persister;

    public MultiBridge(
            MultiBridgeKeeper keeper,
            MultiBridgePersister persister
    ) {
        super(BRIDGE_TYPE);
        this.keeper = keeper;
        this.persister = persister;
    }

    @Override
    public Keeper getKeeper() {
        return keeper;
    }

    @Override
    public Persister getPersister() {
        return persister;
    }

    @Override
    public String toString() {
        return "MultiBridge{" +
                "keeper=" + keeper +
                ", persister=" + persister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
