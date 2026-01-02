# Contributing to Image Compressor App

First off, thank you for considering contributing to Image Compressor App! 🎉

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Coding Guidelines](#coding-guidelines)
- [Submitting Changes](#submitting-changes)

## 📜 Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code: 

- Be respectful and inclusive
- Welcome newcomers and help them learn
- Focus on what is best for the community
- Show empathy towards other community members

## 🤔 How Can I Contribute? 

### Reporting Bugs

Before creating bug reports, please check existing issues to avoid duplicates.

When creating a bug report, include: 

- **Clear title and description**
- **Steps to reproduce** the issue
- **Expected behavior** vs **actual behavior**
- **Screenshots** if applicable
- **Device information** (Android version, device model)
- **App version**

**Example:**
```
Title: App crashes when selecting PNG images over 10MB

Description: The app crashes without warning when attempting to compress PNG images larger than 10MB. 

Steps to Reproduce:
1. Open the app
2. Tap on image selector
3. Select a PNG image over 10MB
4. App crashes

Expected: Image should load and be ready for compression
Actual: App crashes to home screen

Device: Samsung Galaxy S21, Android 12
App Version: 1.0
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, include:

- **Clear title and description**
- **Use case** - why would this be useful?
- **Proposed solution** (if you have one)
- **Alternatives considered**
- **Mockups or examples** (if applicable)

### Pull Requests

Good pull requests (patches, improvements, new features) are a fantastic help. They should remain focused in scope and avoid containing unrelated commits.

**Please ask first** before embarking on any significant pull request, otherwise you risk spending time on something that might not be merged.

## 🛠️ Development Setup

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/ImageCompressorApp.git
   cd ImageCompressorApp
   ```

2. **Open in Android Studio**
   - Use Android Studio Arctic Fox or newer
   - Let Gradle sync complete

3. **Create a branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Make your changes**
   - Write clean, documented code
   - Follow the existing code style
   - Add comments where necessary

5. **Test your changes**
   - Test on multiple Android versions if possible
   - Test on different screen sizes
   - Ensure no crashes or ANRs

## 📝 Coding Guidelines

### Java Style Guide

- **Indentation:** 4 spaces (no tabs)
- **Line length:** Maximum 120 characters
- **Naming conventions:**
  - Classes: `PascalCase` (e.g., `ImageCompression`)
  - Methods: `camelCase` (e.g., `convertImage()`)
  - Variables: `camelCase` (e.g., `imageUri`)
  - Constants: `UPPER_SNAKE_CASE` (e.g., `GOT_ALL_PERMISSIONS`)

### Code Quality

- **Comments:** Add JavaDoc comments for public methods
- **Error Handling:** Always handle exceptions appropriately
- **Resource Management:** Close streams and resources properly
- **Performance:** Avoid memory leaks, especially with Bitmaps

### Example of Good Code Style

```java
/**
 * Compresses the selected image with the specified quality.
 * 
 * @param quality The compression quality (0-100)
 * @throws IOException if the image cannot be written
 */
public void compressImage(int quality) throws IOException {
    if (quality < 0 || quality > 100) {
        throw new IllegalArgumentException("Quality must be between 0 and 100");
    }
    
    // Implementation here
}
```

## 📤 Submitting Changes

1. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add:  Brief description of changes"
   ```

   **Commit message format:**
   - `Add: ` for new features
   - `Fix:` for bug fixes
   - `Update:` for updates to existing features
   - `Refactor:` for code refactoring
   - `Docs:` for documentation changes

2. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

3. **Open a Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Select your fork and branch
   - Fill in the PR template with: 
     - What changes you made
     - Why you made them
     - How to test them
     - Screenshots (if UI changes)

### Pull Request Checklist

Before submitting, ensure: 

- [ ] Code follows the project's style guidelines
- [ ] Comments are added for complex logic
- [ ] No new warnings or errors
- [ ] App builds successfully
- [ ] Changes have been tested on a device/emulator
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up to date with master

## 🏗️ Project-Specific Guidelines

### Adding New Features

When adding new features:

1. Discuss in an issue first
2. Keep features modular and testable
3. Update documentation
4. Add comments explaining the "why" not just the "what"

### Working with Bitmaps

- Always recycle bitmaps when done
- Be mindful of memory usage
- Test with large images (10MB+)
- Handle OutOfMemoryError gracefully

### UI Changes

- Follow Material Design guidelines
- Test on different screen sizes
- Ensure accessibility (content descriptions, touch targets)
- Maintain consistency with existing UI

## 🎯 Priority Areas

We especially welcome contributions in these areas:

- Batch image compression
- Performance optimizations
- Better error handling
- UI/UX improvements
- Documentation improvements
- Test coverage
- Accessibility features

## ❓ Questions?

Feel free to: 
- Open an issue with the `question` label
- Start a discussion
- Reach out to maintainers

Thank you for contributing!  🙌
```