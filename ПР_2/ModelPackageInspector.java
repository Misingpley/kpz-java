package ua.duikt.holoshchuk.pr2.model;

public class ModelPackageInspector {

    public static String inspectFromSamePackage(Book book) {
        String code = book.catalogCode; // package-private: ДОСТУПНО в пакеті model
        int y = book.year;              // protected: ДОСТУПНО в пакеті model
        String t = book.title;          // public: ДОСТУПНО всюди
        return String.format("[З пакета model] public title='%s', protected year=%d, default catalogCode='%s' (private internalNote - недоступне)",
                t, y, code);
    }
}
